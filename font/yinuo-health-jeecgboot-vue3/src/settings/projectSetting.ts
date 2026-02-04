import type { ProjectConfig } from '/#/config';
import { MenuTypeEnum, MenuModeEnum, TriggerEnum, MixSidebarTriggerEnum } from '/@/enums/menuEnum';
import { CacheTypeEnum } from '/@/enums/cacheEnum';
import {
  ContentEnum,
  PermissionModeEnum,
  ThemeEnum,
  RouterTransitionEnum,
  SettingButtonPositionEnum,
  SessionTimeoutProcessingEnum,
  TabsThemeEnum,
} from '/@/enums/appEnum';
import { SIDE_BAR_BG_COLOR_LIST, HEADER_PRESET_BG_COLOR_LIST } from './designSetting';
import { primaryColor } from '../../build/config/themeConfig';

// ! 改动后需要清空浏览器缓存
const setting: ProjectConfig = {
    "showSettingButton": true,
    "showDarkModeToggle": true,
    "settingButtonPosition": "auto",
    "permissionMode": "BACK",
    "permissionCacheType": 1,
    "sessionTimeoutProcessing": 0,
    "themeColor": "#1890FF",
    "grayMode": false,
    "colorWeak": false,
    "fullContent": false,
    "contentMode": "full",
    "showLogo": true,
    "showFooter": false,
    "headerSetting": {
        "bgColor": "#24292e",
        "fixed": true,
        "show": true,
        "theme": "dark",
        "useLockPage": true,
        "showFullScreen": true,
        "showDoc": true,
        "showNotice": true,
        "showSearch": true
    },
    "menuSetting": {
        "bgColor": "#001529",
        "fixed": true,
        "collapsed": false,
        "collapsedShowTitle": false,
        "canDrag": false,
        "show": true,
        "hidden": false,
        "menuWidth": 210,
        "mode": "horizontal",
        "type": "top-menu",
        "theme": "dark",
        "topMenuAlign": "center",
        "trigger": "HEADER",
        "accordion": true,
        "closeMixSidebarOnChange": false,
        "mixSideTrigger": "click",
        "mixSideFixed": false
    },
    "multiTabsSetting": {
        "cache": false,
        "show": true,
        "canDrag": true,
        "showQuick": true,
        "showRedo": true,
        "showFold": true,
        "theme": "card"
    },
    "transitionSetting": {
        "enable": true,
        "basicTransition": "fade-slide",
        "openPageLoading": true,
        "openNProgress": true
    },
    "openKeepAlive": true,
    "lockTime": 0,
    "showBreadCrumb": false,
    "showBreadCrumbIcon": true,
    "useErrorHandle": false,
    "useOpenBackTop": true,
    "canEmbedIFramePage": true,
    "closeMessageOnSwitch": true,
    "removeAllHttpPending": false
};

export default setting;
