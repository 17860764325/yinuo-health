package org.jeecg.modules.doctor.util;

/**
 * @author lihaoran
 * @date 10/18/23 12:04 AM
 */
public class OtherUtil {

    /**
     * @description:  将项目号的LNCT字符去掉
     * @author lhr
     * @date 10/18/23 12:04 AM
     * @version 1.0
     */
    public  static String strControll(String str){
        return str.replace("LNCT","");
    }
}
