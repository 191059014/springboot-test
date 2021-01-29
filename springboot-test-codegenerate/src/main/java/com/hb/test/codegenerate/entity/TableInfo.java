package com.hb.test.codegenerate.entity;

import lombok.Data;

import java.util.Date;

/**
 * 表信息
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Data
public class TableInfo {

    // 表名
    private String tableName;
    // 注释
    private String tableComment;
    // 引擎
    private String engine;
    // 字符集
    private String tableCollation;
    // 创建时间
    private String createTime;

}
