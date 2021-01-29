package com.hb.test.codegenerate.controller;

import com.hb.test.codegenerate.common.Page;
import com.hb.test.codegenerate.common.R;
import com.hb.test.codegenerate.common.ResultCode;
import com.hb.test.codegenerate.entity.TableInfo;
import com.hb.test.codegenerate.exception.BusinessException;
import com.hb.test.codegenerate.service.ICodegenerateService;
import com.hb.test.codegenerate.vo.CodegenerateVo;
import com.hb.test.codegenerate.vo.TableQueryVo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 生成代码控制层
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@RestController
@RequestMapping("/codegenerator")
public class CodegenerateController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CodegenerateController.class);

    /**
     * 代码生成服务
     */
    @Autowired
    private ICodegenerateService iCodegenerateService;

    /**
     * 列表
     *
     * @param tableQueryVo
     *            参数集
     * @return 数据库表
     */
    @PostMapping("/findTablePages")
    public R<Page<TableInfo>> findTablePages(@RequestBody TableQueryVo tableQueryVo) {
        try {
            Page<TableInfo> page = iCodegenerateService.findTablePages(tableQueryVo);
            return new R<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), page);
        } catch (BusinessException e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("分页查询业务异常={}", e.toString());
            }
            return new R<>(e.getCode(), e.getMsg(), null);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("分页查询系统异常=", e);
            }
            return new R<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), null);
        }

    }

    /**
     * 生成代码
     *
     * @param codegenerateVo
     *            请求参数
     * @param response
     *            响应
     */
    @PostMapping("/generatorCode")
    public void generatorCode(@RequestBody CodegenerateVo codegenerateVo, HttpServletResponse response) {
        try {
            byte[] data = iCodegenerateService.generatorCode(codegenerateVo);
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=code.zip");
            response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
        } catch (BusinessException e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("分页查询业务异常={}", e.toString());
            }
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("生成代码系统异常=", e);
            }
        }
    }

}
