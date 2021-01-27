package com.hb.test.codegenerate.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Data
@AllArgsConstructor
public class Page<T> {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> list;

}
