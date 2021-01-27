package com.hb.test.codegenerate.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成vo
 *
 * @version v0.1, 2021/1/27 14:48, create by huangbiao.
 */
@Data
public class CodegenerateVo implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 6547450474995806005L;
    // 请求参数
    private TableQueryVo tableQueryVo;
    // 包名
    private String packageName;
    // 作者
    private String author;
    // 模块名称
    private String moduleName;
    // 表前缀
    private String tablePrefix;
    // 表名称
    private String tableName;
    // 表备注
    private String comments;

}