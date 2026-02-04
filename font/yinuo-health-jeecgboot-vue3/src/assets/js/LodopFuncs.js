import { useMessage } from '@/hooks/web/useMessage';
const { createMessage, createErrorModal, createConfirm } = useMessage();

/**
 * Lodop打印插件/服务加载与初始化模块
 * 支持多种浏览器环境，按需加载
 */
let ip = '127.0.0.1'; // 默认打印服务器IP地址
let isInitialized = false; // 初始化状态标记
let lodopInitPromise = null; // Lodop初始化Promise

// 定义加载资源URL，支持多端口防止阻塞
const RESOURCE_CONFIG = {
  jsFileName: 'CLodopfuncs.js',
  wsPorts: [8000, 18000],
  httpPorts: [8000, 18000],
  httpsPort: 8443,
};

let CreatedOKLodopObject = null; // 已创建的Lodop对象
let CLodopIsLocal = false; // 是否本地环境
let LoadJsState = ''; // 脚本加载状态

/**
 * 版本号比较函数
 * @param {string} v1 - 版本号1
 * @param {string} v2 - 版本号2
 * @returns {number} - 返回 -1(v1<v2), 0(v1=v2), 1(v1>v2)
 */
function compareVersion(v1, v2) {
  if (!v1 || !v2) return 0;
  
  const parts1 = v1.split('.').map(n => parseInt(n) || 0);
  const parts2 = v2.split('.').map(n => parseInt(n) || 0);
  const maxLength = Math.max(parts1.length, parts2.length);
  
  for (let i = 0; i < maxLength; i++) {
    const num1 = parts1[i] || 0;
    const num2 = parts2[i] || 0;
    if (num1 > num2) return 1;
    if (num1 < num2) return -1;
  }
  
  return 0;
}

/**
 * 检查CLodop版本是否需要更新
 * @param {object} LODOP - Lodop对象
 * @param {boolean} isLinuxX86 - 是否Linux X86平台
 * @param {boolean} isLinuxARM - 是否Linux ARM平台
 * @returns {boolean} - 是否需要更新
 */
function checkVersionNeedUpdate(LODOP, isLinuxX86, isLinuxARM) {
  if (!LODOP || !LODOP.CVERSION) return false;
  
  // Linux平台最低版本要求
  if (isLinuxX86 || isLinuxARM) {
    return compareVersion(LODOP.CVERSION, '7.0.4.3') < 0;
  }
  
  // Windows平台CLodop最低版本要求（兼容4.x版本）
  // 4.0.8.8 是一个有效的版本，不需要升级到 6.5.7.7
  if (window.CLODOP && CLODOP.CVERSION) {
    const currentVer = CLODOP.CVERSION;
    // 只要求4.x系列高于4.0.8.0即可，或者6.x系列高于6.5.0.0
    const majorVersion = parseInt(currentVer.split('.')[0]);
    if (majorVersion === 4) {
      return compareVersion(currentVer, '4.0.8.0') < 0;
    } else if (majorVersion >= 6) {
      return compareVersion(currentVer, '6.5.0.0') < 0;
    }
  }
  
  return false;
}

/**
 * 判断是否需要使用CLodop服务
 * 主要针对不支持ActiveX插件的现代浏览器
 */
function needCLodop() {
  try {
    const ua = navigator.userAgent;

    // 移动设备和特定现代浏览器直接使用CLodop
    if (ua.match(/Windows\sPhone/i) || ua.match(/iPhone|iPod|iPad/i) || ua.match(/Android/i) || ua.match(/Edge\D?\d+/i)) {
      return true;
    }

    // 检查IE版本
    const verTrident = ua.match(/Trident\D?\d+/i);
    const verIE = ua.match(/MSIE\D?\d+/i);

    // 360浏览器检测（包括极速模式和兼容模式）
    const is360 = ua.match(/360SE|360EE/i) || ua.match(/QIHU 360/i);
    
    // 检查其他浏览器版本
    const verOPR = ua.match(/OPR\D?\d+/i);
    const verFF = ua.match(/Firefox\D?\d+/i);
    const x64 = ua.match(/x64/i);

    // 360浏览器强制使用CLodop，避免ActiveX插件兼容性问题
    if (is360) {
      console.log('检测到360浏览器，使用CLodop服务');
      return true;
    }

    // 根据浏览器类型和版本决定是否需要CLodop
    if (!verTrident && !verIE && x64) return true;
    if (verFF) {
      const ffVer = parseInt(verFF[0].match(/\d+/)[0]);
      if (ffVer >= 41 || x64) return true;
    }
    if (verOPR) {
      const oprVer = parseInt(verOPR[0].match(/\d+/)[0]);
      if (oprVer >= 32) return true;
    }
    if (!verTrident && !verIE) {
      const verChrome = ua.match(/Chrome\D?\d+/i);
      if (verChrome) {
        const chromeVer = parseInt(verChrome[0].match(/\d+/)[0]);
        if (chromeVer >= 41) return true;
      }
    }

    return false;
  } catch (err) {
    return true;
  }
}

/**
 * 检查并尝试通过HTTP方式加载Lodop
 */
function checkOrTryHttp() {
  if (window.getCLodop) {
    LoadJsState = 'complete';
    return true;
  }

  if (LoadJsState === 'loadingB' || LoadJsState === 'complete') return;
  LoadJsState = 'loadingB';

  const head = document.head || document.getElementsByTagName('head')[0] || document.documentElement;
  const { httpUrls } = generateResourceUrls();

  const scripts = httpUrls.map((url) => {
    const script = document.createElement('script');
    script.src = url;
    script.onload = () => (LoadJsState = 'complete');
    script.onerror = () => {}; // 忽略单个URL加载失败
    return script;
  });

  // 按优先级顺序加载
  head.insertBefore(scripts[0], head.firstChild);
  scripts[0].onerror = () => {
    if (window.location.protocol !== 'https:') {
      head.insertBefore(scripts[1], head.firstChild);
    } else {
      head.insertBefore(scripts[2], head.firstChild);
    }
  };
}

/**
 * 生成资源加载URL列表
 */
const generateResourceUrls = () => {
  const { jsFileName, wsPorts, httpPorts, httpsPort } = RESOURCE_CONFIG;
  return {
    wsUrls: wsPorts.map((port) => `ws://${ip}:${port}/${jsFileName}`),
    httpUrls: [...httpPorts.map((port) => `http://${ip}:${port}/${jsFileName}`), `https://localhost.lodop.net:${httpsPort}/${jsFileName}`],
  };
};

/**
 * 初始化加载CLodop服务
 * @param {string} newIp - 新的IP地址
 * @returns {Promise} 返回初始化完成的Promise
 */
function initializeLodop(newIp) {
  // 如果已经在初始化中，返回现有Promise
  if (lodopInitPromise && (!newIp || newIp === ip)) {
    return lodopInitPromise;
  }

  // 如果已经初始化完成且IP没有变化，直接返回成功Promise
  if (isInitialized && (!newIp || newIp === ip) && LoadJsState === 'complete') {
    return Promise.resolve();
  }

  // 如果IP变化了，重置初始化状态
  if (newIp && newIp !== ip) {
    isInitialized = false;
    lodopInitPromise = null;
    LoadJsState = '';
  }

  lodopInitPromise = new Promise((resolve, reject) => {
    // 更新IP地址
    if (newIp) {
      ip = newIp;
      console.log(`初始化Lodop使用IP: ${ip}`);
    }

    if (!needCLodop()) {
      isInitialized = true;
      resolve();
      return;
    }

    const { wsUrls } = generateResourceUrls();
    CLodopIsLocal = !!wsUrls[0].match(/\/\/localho|\/\/127.0.0./i);
    LoadJsState = 'loadingA';

    // 兼容Firefox旧版本和老版本浏览器
    if (!window.WebSocket && window.MozWebSocket) {
      window.WebSocket = window.MozWebSocket;
    }

    let checkInterval;
    const maxWaitTime = 15000; // 最多等待15秒（老版本浏览器需要更长时间）
    const startTime = Date.now();

    // 定期检查加载状态
    checkInterval = setInterval(() => {
      if (LoadJsState === 'complete' || window.getCLodop) {
        clearInterval(checkInterval);
        isInitialized = true;
        resolve();
      } else if (Date.now() - startTime > maxWaitTime) {
        clearInterval(checkInterval);
        reject(new Error('Lodop加载超时'));
      }
    }, 100);

    // 使用WebSocket方式加载，速度更快
    const tryWsConnection = (url, nextUrl) => {
      // 检查WebSocket是否可用（老版本360浏览器可能不支持）
      if (!window.WebSocket) {
        console.log('浏览器不支持WebSocket，直接使用HTTP方式加载');
        checkOrTryHttp();
        return;
      }

      try {
        const ws = new WebSocket(url);
        ws.onopen = () => setTimeout(checkOrTryHttp, 200);
        ws.onmessage = (e) => {
          if (!window.getCLodop) {
            try {
              // 使用Function构造器代替eval，更安全
              const func = new Function(e.data);
              func();
            } catch (err) {
              console.error('执行CLodop脚本失败:', err);
            }
          }
        };
        ws.onerror = () => {
          if (nextUrl) {
            tryWsConnection(nextUrl);
          } else {
            checkOrTryHttp();
          }
        };
      } catch (e) {
        console.log('WebSocket连接失败，尝试下一个URL或HTTP方式');
        if (nextUrl) {
          tryWsConnection(nextUrl);
        } else {
          checkOrTryHttp();
        }
      }
    };

    tryWsConnection(wsUrls[0], wsUrls[1]);
  });

  return lodopInitPromise;
}

/**
 * 获取Lodop对象实例（异步版本，推荐使用）
 * @param {string} dictIp - 打印服务器IP地址
 * @returns {Promise<object>} - Lodop对象实例Promise
 */
async function getLodopAsync(dictIp) {
  try {
    // 等待初始化完成
    await initializeLodop(dictIp);

    // 获取Lodop对象
    return getLodop(dictIp);
  } catch (err) {
    console.error('Lodop初始化失败:', err);
    throw err;
  }
}

/**
 * 获取Lodop对象实例（同步版本，兼容旧代码）
 * @param {string} dictIp - 打印服务器IP地址
 * @param {object} oOBJECT - 可选的object元素
 * @param {object} oEMBED - 可选的embed元素
 * @returns {object} - Lodop对象实例
 */
function getLodop(dictIp, oOBJECT, oEMBED) {
  // 更新IP地址并初始化（非阻塞）
  if (dictIp && dictIp !== ip) {
    initializeLodop(dictIp);
  } else if (!isInitialized) {
    initializeLodop();
  }

  // 定义提示信息
  const installMessages = {
    plugin: {
      install32: "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop32.exe' target='_self'>执行安装</a>",
      install64: "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop64.exe' target='_self'>执行安装</a>",
      update32: "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop32.exe' target='_self'>执行升级</a>",
      update64: "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop64.exe' target='_self'>执行升级</a>",
    },
    service: {
      install:
        "<br><font color='#FF00FF'>Web打印服务CLodop未安装启动，点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>下载执行安装</a>",
      installLocal: "<br>（若此前已安装过，可<a href='CLodop.protocol:setup' target='_self'>点这里直接再次启动</a>）",
      update: "<br><font color='#FF00FF'>Web打印服务CLodop需升级!点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>执行升级</a>",
    },
    linux: {
      installX86:
        "<br><font color='#FF00FF'>Web打印服务Lodop7未安装启动，点击这里<a href='Lodop7_Linux_X86_64.tar.gz' target='_self'>下载安装</a>(下载后解压，点击lodop文件开始执行)",
      installARM:
        "<br><font color='#FF00FF'>Web打印服务Lodop7未安装启动，点击这里<a href='Lodop7_Linux_ARM64.tar.gz'  target='_self'>下载安装</a>(下载后解压，点击lodop文件开始执行)",
      updateX86:
        "<br><font color='#FF00FF'>Web打印服务Lodop7需升级，点击这里<a href='Lodop7_Linux_X86_64.tar.gz' target='_self'>下载安装</a>(下载后解压，点击lodop文件开始执行)",
      updateARM:
        "<br><font color='#FF00FF'>Web打印服务Lodop7需升级，点击这里<a href='Lodop7_Linux_ARM64.tar.gz'  target='_self'>下载安装</a>(下载后解压，点击lodop文件开始执行)",
    },
    installSuffix: '，成功后请刷新本页面或重启浏览器。</font>',
  };

  try {
    // 检测浏览器环境
    const isWinIE = /MSIE/i.test(navigator.userAgent) || /Trident/i.test(navigator.userAgent);
    const isWinIE64 = isWinIE && /x64/i.test(navigator.userAgent);
    const isLinuxX86 = /Linux/i.test(navigator.platform) && /x86/i.test(navigator.platform);
    const isLinuxARM = /Linux/i.test(navigator.platform) && /aarch/i.test(navigator.platform);

    let LODOP;

    // 现代浏览器或Linux系统使用CLodop服务
    if (needCLodop() || isLinuxX86 || isLinuxARM) {
      try {
        LODOP = window.getCLodop();
      } catch (err) {
        // 忽略错误，后续处理
      }

      // 检查加载状态 - 只在初始加载时等待
      if (!LODOP && LoadJsState !== 'complete') {
        if (!LoadJsState) {
          alert('未曾加载Lodop主JS文件，请先调用loadCLodop过程.');
        } else {
          // 不显示警告，因为初始化是异步的
          console.log('Lodop正在加载中...');
        }
        return;
      }

      // 处理未安装情况
      if (!LODOP) {
        let message = isLinuxX86
          ? installMessages.linux.installX86
          : isLinuxARM
          ? installMessages.linux.installARM
          : installMessages.service.install + (CLodopIsLocal ? installMessages.service.installLocal : '');
        
        // 使用消息提示替代直接修改body，避免破坏Vue应用
        console.error('CLodop未安装:', message);
        createErrorModal({
          title: 'CLodop打印服务未安装',
          content: message + installMessages.installSuffix,
        });
        return;
      }

      // 处理需要升级情况 - 修复版本比较逻辑
      else {
        const needUpdate = checkVersionNeedUpdate(LODOP, isLinuxX86, isLinuxARM);
        if (needUpdate) {
          let message = isLinuxX86 ? installMessages.linux.updateX86 : isLinuxARM ? installMessages.linux.updateARM : installMessages.service.update;
          console.warn('CLodop需要升级:', message);
          createMessage.warning(message.replace(/<[^>]*>/g, '') + installMessages.installSuffix.replace(/<[^>]*>/g, ''));
        }
      }
    }

    // IE浏览器使用插件方式
    else {
      // 使用已有元素或创建新元素
      if (oOBJECT || oEMBED) {
        LODOP = isWinIE ? oOBJECT : oEMBED;
      } else if (!CreatedOKLodopObject) {
        LODOP = document.createElement('object');
        LODOP.setAttribute('width', 0);
        LODOP.setAttribute('height', 0);
        LODOP.setAttribute('style', 'position:absolute;left:0px;top:-100px;width:0px;height:0px;');
        LODOP.setAttribute(isWinIE ? 'classid' : 'type', isWinIE ? 'clsid:2105C259-1E0C-4534-8141-A753534CB4CA' : 'application/x-print-lodop');
        document.documentElement.appendChild(LODOP);
        CreatedOKLodopObject = LODOP;
      } else {
        LODOP = CreatedOKLodopObject;
      }

      // 处理未安装或需要升级情况
      if (!LODOP || !LODOP.VERSION) {
        const message = (isWinIE64 ? installMessages.plugin.install64 : installMessages.plugin.install32) + installMessages.installSuffix;
        console.error('Lodop插件未安装:', message);
        createErrorModal({
          title: 'Lodop打印插件未安装',
          content: message,
        });
        return LODOP;
      }

      // 修复版本比较：将版本字符串转换为数字数组进行比较
      if (compareVersion(LODOP.VERSION, '6.2.2.6') < 0) {
        const message = (isWinIE64 ? installMessages.plugin.update64 : installMessages.plugin.update32) + installMessages.installSuffix;
        console.warn('Lodop插件需要升级:', message);
        createMessage.warning(message.replace(/<[^>]*>/g, ''));
      }
    }

    // 设置许可证
    // 192.168.68.2 ip的密钥
    if (LODOP) {
      LODOP.SET_LICENSES('', '15F0BE661EA82FC17491843CB303EB11', '', '');
    }
    return LODOP;
  } catch (err) {
    alert('getLodop出错:' + err);
  }
}

export { getLodop, getLodopAsync };
