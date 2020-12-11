package com.hb.test.sharding.jdbc.util;

import com.hb.test.sharding.jdbc.common.DbAndTbEnum;

import java.math.BigDecimal;

/**
 * 分库分表工具类
 *
 * @version v0.1, 2020/12/11 10:51, create by huangbiao.
 */
public class ShardingUtils {

    /**
     * 数据源名称的前缀长度
     */
    public static final int DATASOURCE_NAME_PREFIX_LENGTH = 2;

    /**
     * 数据库索引长度
     */
    public static final int DB_INDEX_LENGTH = 2;

    /**
     * 表索引长度
     */
    public static final int TB_INDEX_LENGTH = 4;

    /**
     * 获取字符串的asc码
     *
     * @param str
     *            字符串
     * @return asc码
     */
    private static String getAscii(String str) {
        StringBuilder indexSb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char[] strChar = str.substring(i, i + 1).toCharArray();
            for (char s : strChar) {
                indexSb.append((byte)s);
            }
        }
        return indexSb.toString();
    }

    public static String getDbIndexByRoutingKey(String routingValue, DbAndTbEnum dbAndTbEnum) {
        String ascii = getAscii(routingValue);
        BigDecimal[] divideAndRemainder =
            new BigDecimal(ascii).divideAndRemainder(BigDecimal.valueOf(dbAndTbEnum.getDbCount()));
        return fillZero(divideAndRemainder[1], DB_INDEX_LENGTH, true);
    }

    public static String getDbIndexByShardingKey(String shardingValue, DbAndTbEnum dbAndTbEnum) {
        return shardingValue.substring(dbAndTbEnum.getDbIndexBegin(), dbAndTbEnum.getDbIndexBegin() + DB_INDEX_LENGTH);
    }

    public static String getTbIndexByRoutingKey(String routingValue, DbAndTbEnum dbAndTbEnum) {
        String ascii = getAscii(routingValue);
        BigDecimal[] divideAndRemainder =
            new BigDecimal(ascii).divideAndRemainder(BigDecimal.valueOf(dbAndTbEnum.getTbCount()));
        return fillZero(divideAndRemainder[1], TB_INDEX_LENGTH, true);
    }

    public static String getTbIndexByShardingKey(String shardingValue, DbAndTbEnum dbAndTbEnum) {
        return shardingValue.substring(dbAndTbEnum.getTbIndexBegin(), dbAndTbEnum.getTbIndexBegin() + TB_INDEX_LENGTH);
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

}
