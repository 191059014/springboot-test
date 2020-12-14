package com.hb.test.sharding.jdbc.common;

/**
 * 分库分表规则
 *
 * @version v0.1, 2020/12/10 18:05, create by huangbiao.
 */
public enum DbAndTbEnum {

    T_ORDER("t_order", "order_id", "OD", 3, 6, 2, 4, "订单表"),
    T_USER("t_user", "user_id", "UID", 1, 3, 2, 5, "用户表"),
    ;

    /**
     * 分片表名
     */
    private String logicTableName;
    /**
     * 分片键
     */
    private String shardingKey;
    /**
     * 字段值前缀
     */
    private String keyPrefix;
    /**
     * 分布所在库数量
     */
    private int dbCount;
    /**
     * 分布所在表数量-所有库中表数量总计
     */
    private int tbCount;
    /**
     * 数据库索引位开始下标索引
     */
    private int dbIndexBegin;
    /**
     * 表索引位开始下标索引
     */
    private int tbIndexBegin;
    /**
     * 描述
     */
    private String desc;

    DbAndTbEnum(String logicTableName, String shardingKey, String keyPrefix, int dbCount, int tbCount, int dbIndexBegin,
        int tbIndexBegin, String desc) {
        this.logicTableName = logicTableName;
        this.shardingKey = shardingKey;
        this.keyPrefix = keyPrefix;
        this.dbCount = dbCount;
        this.tbCount = tbCount;
        this.dbIndexBegin = dbIndexBegin;
        this.tbIndexBegin = tbIndexBegin;
        this.desc = desc;
    }

    public String getLogicTableName() {
        return logicTableName;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public int getDbCount() {
        return dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public int getDbIndexBegin() {
        return dbIndexBegin;
    }

    public int getTbIndexBegin() {
        return tbIndexBegin;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 通过逻辑表名获取
     *
     * @param logicTableName
     *            逻辑表名
     * @return DbAndTbEnum
     */
    public static DbAndTbEnum getByLogicTableName(String logicTableName) {
        for (DbAndTbEnum dbAndTbEnum : DbAndTbEnum.values()) {
            if (dbAndTbEnum.getLogicTableName().equals(logicTableName)) {
                return dbAndTbEnum;
            }
        }
        return null;
    }

}
