package com.hb.test.codegenerate.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询表vo
 *
 * @version v0.1, 2021/1/27 14:48, create by huangbiao.
 */
@Data
public class TableQueryVo implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -6782288991055396590L;

    // 数据库连接
    private String url;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 表名
    private String tableName;
    // 当前页
    private Integer currentPage;
    // 每页条数
    private Integer pageSize;

}
