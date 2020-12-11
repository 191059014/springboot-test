package com.hb.test.sharding.jdbc.util;

import com.hb.test.sharding.jdbc.common.DbAndTbEnum;

/**
 * 生成key
 *
 * @version v0.1, 2020/12/11 16:19, create by huangbiao.
 */
public class KeyGenerator {

    public static String getUniqKey(String routeValue, DbAndTbEnum dbAndTbEnum) {
        String dbIndex = ShardingUtils.getDbIndexByRoutingKey(routeValue, dbAndTbEnum);
        String tbIndex = ShardingUtils.getTbIndexByRoutingKey(routeValue, dbAndTbEnum);
        System.out.println("dbIndex=" + dbIndex + ", tbIndex=" + tbIndex);
        return dbAndTbEnum.getKeyPrefix() + dbIndex + tbIndex + System.currentTimeMillis();
    }

}
