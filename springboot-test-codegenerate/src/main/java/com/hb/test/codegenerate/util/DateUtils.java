package com.hb.test.codegenerate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @version v0.1, 2021/1/27 20:02, create by huangbiao.
 */
public class DateUtils {

    /**
     * 获取当前时间
     * 
     * @return 时间字符串
     */
    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
