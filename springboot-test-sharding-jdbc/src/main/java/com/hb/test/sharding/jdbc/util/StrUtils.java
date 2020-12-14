package com.hb.test.sharding.jdbc.util;

import java.math.BigDecimal;

/**
 * 字符串工具类
 *
 * @version v0.1, 2020/12/14 9:45, create by huangbiao.
 */
public class StrUtils {

    /**
     * 先对指定对象取ASCII码后取模运算
     *
     * @param obj
     *            被除数
     * @param numObj
     *            除数
     * @return 余数
     */
    public static long getModValue(Object obj, Object numObj) {
        String str = getAscii(obj);
        BigDecimal bc = new BigDecimal(str);
        BigDecimal[] results = bc.divideAndRemainder(new BigDecimal(numObj.toString()));
        return results[1].longValue();
    }

    /**
     * 在左边填充0
     *
     * @param source
     *            原字符串
     * @param targetLength
     *            目标长度
     * @return 字符串
     */
    public static String fillZeroAtLeft(Object source, int targetLength) {
        return fillZero(source, targetLength, true);
    }

    /**
     * 在右边填充0
     *
     * @param source
     *            原字符串
     * @param targetLength
     *            目标长度
     * @return 字符串
     */
    public static String fillZeroAtRight(Object source, int targetLength) {
        return fillZero(source, targetLength, false);
    }

    /**
     * 填充0在开头或结尾
     *
     * @param source
     *            原字符串
     * @param targetLength
     *            目标长度
     * @param fillZeroAtLeft
     *            是否补0在起始位置
     * @return 字符串
     */
    public static String fillZero(Object source, int targetLength, boolean fillZeroAtLeft) {
        if (source == null) {
            return null;
        }
        String s = source.toString();
        if (s.length() > targetLength) {
            return s.substring(0, targetLength);
        }
        StringBuilder zeroSb = new StringBuilder();
        for (int i = 0; i < targetLength - s.length(); i++) {
            zeroSb.append("0");
        }
        if (fillZeroAtLeft) {
            zeroSb.append(source);
        } else {
            zeroSb.insert(0, source);
        }
        return zeroSb.toString();
    }

    /**
     * 获取asc码
     *
     * @param obj
     *            obj
     *
     * @return asc码
     */
    public static String getAscii(Object obj) {
        String str = obj == null ? "" : obj.toString();
        if (!hasText(str)) {
            return "0";
        }
        StringBuilder indexSb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char[] strChar = str.substring(i, i + 1).toCharArray();
            for (char s : strChar) {
                indexSb.append((byte)s);
            }
        }
        return indexSb.toString();
    }

    /**
     * 字符串是否包含文本
     *
     * @param str
     *            原字符串
     * @return true
     */
    public static boolean hasText(String str) {
        return str != null && str.trim().length() > 0;
    }

}
