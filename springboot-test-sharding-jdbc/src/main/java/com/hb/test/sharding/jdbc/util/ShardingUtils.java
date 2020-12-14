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
     * 根据路由key获取库索引
     * 
     * @param routingValue
     *            路由key的值
     * @param dbAndTbEnum
     *            分库分表配置枚举
     * @return 库索引
     */
    public static String getDbIndexByRoutingKey(String routingValue, DbAndTbEnum dbAndTbEnum) {
        String ascii = StrUtils.getAscii(routingValue);
        BigDecimal[] divideAndRemainder =
            new BigDecimal(ascii).divideAndRemainder(BigDecimal.valueOf(dbAndTbEnum.getDbCount()));
        return StrUtils.fillZero(divideAndRemainder[1], DB_INDEX_LENGTH, true);
    }

    /**
     * 根据分片键获取库索引
     *
     * @param shardingValue
     *            分片键的值
     * @param dbAndTbEnum
     *            分库分表配置枚举
     * @return 库索引
     */
    public static String getDbIndexByShardingKey(String shardingValue, DbAndTbEnum dbAndTbEnum) {
        return shardingValue.substring(dbAndTbEnum.getDbIndexBegin(), dbAndTbEnum.getDbIndexBegin() + DB_INDEX_LENGTH);
    }

    /**
     * 根据路由key获取表索引
     *
     * @param routingValue
     *            路由key的值
     * @param dbAndTbEnum
     *            分库分表配置枚举
     * @return 表索引
     */
    public static String getTbIndexByRoutingKey(String routingValue, DbAndTbEnum dbAndTbEnum) {
        int tableNumPerDb = dbAndTbEnum.getTbCount() / dbAndTbEnum.getDbCount();
        long modValue = StrUtils.getModValue(routingValue, tableNumPerDb);
        return StrUtils.fillZero(modValue, TB_INDEX_LENGTH, true);
    }

    /**
     * 根据分片键获取表索引
     *
     * @param shardingValue
     *            分片键的值
     * @param dbAndTbEnum
     *            分库分表配置枚举
     * @return 表索引
     */
    public static String getTbIndexByShardingKey(String shardingValue, DbAndTbEnum dbAndTbEnum) {
        return shardingValue.substring(dbAndTbEnum.getTbIndexBegin(), dbAndTbEnum.getTbIndexBegin() + TB_INDEX_LENGTH);
    }

}
