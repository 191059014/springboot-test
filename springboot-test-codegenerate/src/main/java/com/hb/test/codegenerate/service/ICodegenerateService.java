package com.hb.test.codegenerate.service;

import com.hb.test.codegenerate.common.Page;
import com.hb.test.codegenerate.entity.TableInfo;
import com.hb.test.codegenerate.vo.CodegenerateVo;
import com.hb.test.codegenerate.vo.TableQueryVo;

/**
 * 生成代码服务层接口
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
public interface ICodegenerateService {

    /**
     * 分页查询表信息
     * 
     * @param tableQueryVo
     *            查询条件
     * @return 分页信息
     */
    Page<TableInfo> findTablePages(TableQueryVo tableQueryVo) throws Exception;

    /**
     * 生成代码
     * 
     * @param codegenerateVo
     *            代码生成相关条件
     * @return 字节数组
     */
    byte[] generatorCode(CodegenerateVo codegenerateVo) throws Exception;

}
