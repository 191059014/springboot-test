package com.hb.test.codegenerate.entity;

import lombok.Data;

/**
 * 列信息
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Data
public class ColumnInfo {

    // 列名
    private String columnName;
    // 数据类型
    private String dataType;
    // 备注
    private String columnComment;
    // 备注
    private String columnKey;
    // 其他信息
    private String extra;

    /**
     * Java属性类型
     */
    private String attrType;

    /**
     * Java属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrname;

    /**
     * Java属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String attrName;

}
