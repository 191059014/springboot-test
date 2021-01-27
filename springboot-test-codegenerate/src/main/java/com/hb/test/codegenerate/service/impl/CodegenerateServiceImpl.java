package com.hb.test.codegenerate.service.impl;

import com.hb.test.codegenerate.common.Page;
import com.hb.test.codegenerate.entity.TableInfo;
import com.hb.test.codegenerate.service.ICodegenerateService;
import com.hb.test.codegenerate.utils.DbUtils;
import com.hb.test.codegenerate.vo.TableQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成代码服务层接口实现类
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Service
public class CodegenerateServiceImpl implements ICodegenerateService {

    /**
     * 分页查询数据库中所有表
     */
    private final String SELECT_TABLES_ALL =
        "select table_name tableName, table_comment tableComment, engine, table_collation tableCollation, create_time createTime from information_schema.tables where table_schema = (select database()) order by create_time desc limit %s,%s";

    /**
     * 分页查询数据库中指定表名的表
     */
    private final String SELECT_TABLES_BY_TABLENAME =
        "select table_name tableName, table_comment tableComment, engine, table_collation tableCollation, create_time createTime from information_schema.tables where table_schema = (select database()) and table_name = '%s' order by create_time desc limit %s,%s";

    /**
     * 查询数据库中所有表的总条数
     */
    private final String SELECT_TABLES_ALL_COUNT =
        "select count(*) total from information_schema.tables where table_schema = (select database())";

    /**
     * 查询数据库中指定表名的总条数
     */
    private final String SELECT_TABLES_BY_TABLENAME_COUNT =
        "select count(*) total from information_schema.tables where table_schema = (select database()) and table_name = '%s'";

    @Override
    public Page<TableInfo> findTablePages(TableQueryVo vo) throws Exception {
        DataSource dataSource = DbUtils.dynamicCreateDatasource(vo.getUrl(), vo.getUsername(), vo.getPassword());
        try (Connection connection = dataSource.getConnection()) {
            /*
             * 获取分页查询sql、查询总条数sql
             */
            String selectTablesCountSql = SELECT_TABLES_ALL_COUNT;
            int startRows = (vo.getCurrentPage() - 1) * vo.getPageSize();
            String selectTablesSql = String.format(SELECT_TABLES_ALL, startRows, vo.getPageSize());
            if (StringUtils.isNotBlank(vo.getTableName())) {
                selectTablesCountSql = String.format(SELECT_TABLES_BY_TABLENAME_COUNT, vo.getTableName());
                selectTablesSql =
                    String.format(SELECT_TABLES_BY_TABLENAME, vo.getTableName(), startRows, vo.getPageSize());
            }
            /*
             * 查询总条数
             */
            ResultSet countRs = DbUtils.executeQuery(connection, selectTablesCountSql);
            Long total = 0L;
            while (countRs.next()) {
                total = countRs.getLong("total");
            }
            /*
             * 查询数据集合
             */
            ResultSet dataRs = DbUtils.executeQuery(connection, selectTablesSql);
            List<TableInfo> list = new ArrayList<>();
            TableInfo tableInfo = null;
            while (dataRs.next()) {
                tableInfo = new TableInfo();
                tableInfo.setTableName(dataRs.getString("tableName"));
                tableInfo.setTableComment(dataRs.getString("tableComment"));
                tableInfo.setEngine(dataRs.getString("engine"));
                tableInfo.setTableCollation(dataRs.getString("tableCollation"));
                tableInfo.setCreateTime(dataRs.getDate("createTime"));
                list.add(tableInfo);
            }
            /*
             * 返回分页结果
             */
            return new Page<>(total, list);
        }
    }

}
