package com.hb.test.sharding.jdbc.util;

import com.hb.test.sharding.jdbc.common.DbAndTbEnum;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

/**
 * 通用复杂分片算法-表路由
 *
 * @version v0.1, 2020/12/10 17:47, create by huangbiao.
 */
public class CommonCSATbRule implements ComplexKeysShardingAlgorithm {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonCSATbRule.class);

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
        Collection<ShardingValue> shardingValues) {
        /*
         *  1、availableTargetNames类型是LinkedHashSet，示例：["t_order_0000","t_order_0001","t_order_0003"]
         *  2、shardingValues类型是ArrayList，ShardingValue实现类是ListShardingValue，
         *  ListShardingValue包含：
         *      logicTableName=t_order,
         *      columnName=order_id,
         *      values=[OD123456, OD654321])
         */
        Collection<String> finalTargetNames = new HashSet<>();
        for (ShardingValue shardingValue : shardingValues) {
            ListShardingValue listShardingValue = (ListShardingValue)shardingValue;
            // 逻辑表名
            String logicTableName = listShardingValue.getLogicTableName();
            // 分片键的列名
            String columnName = listShardingValue.getColumnName();
            // 分片键的值
            Collection<String> columnValues = listShardingValue.getValues();
            // 根据逻辑表名找出分库分表的配置
            DbAndTbEnum dbAndTbEnum = DbAndTbEnum.getByLogicTableName(logicTableName);
            if (dbAndTbEnum == null) {
                throw new IllegalArgumentException("无法找到分库分表配置： logicTableName=" + logicTableName);
            }
            for (String columnValue : columnValues) {
                // 获取数据库索引
                String tbIndex = dbAndTbEnum.getShardingKey().equals(columnName)
                    ? ShardingUtils.getTbIndexByShardingKey(columnValue, dbAndTbEnum)
                    : ShardingUtils.getTbIndexByRoutingKey(columnValue, dbAndTbEnum);
                // 根据库索引得到数据源
                availableTargetNames.stream().filter(availableTargetName -> {
                    String tableNameIndex = availableTargetName.substring(logicTableName.length() + 1);
                    return tbIndex.equals(tableNameIndex);
                }).forEach(finalTargetNames::add);
            }

        }
        // 返回数据源列表
        return finalTargetNames;
    }

}
