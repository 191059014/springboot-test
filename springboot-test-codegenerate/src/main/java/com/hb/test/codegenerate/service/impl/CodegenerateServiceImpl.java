package com.hb.test.codegenerate.service.impl;

import com.hb.test.codegenerate.common.Page;
import com.hb.test.codegenerate.entity.ColumnInfo;
import com.hb.test.codegenerate.entity.TableInfo;
import com.hb.test.codegenerate.service.ICodegenerateService;
import com.hb.test.codegenerate.util.DateUtils;
import com.hb.test.codegenerate.util.DbUtils;
import com.hb.test.codegenerate.util.GenerateUtils;
import com.hb.test.codegenerate.vo.CodegenerateVo;
import com.hb.test.codegenerate.vo.TableQueryVo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 生成代码服务层接口实现类
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Service
public class CodegenerateServiceImpl implements ICodegenerateService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CodegenerateServiceImpl.class);

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

    /**
     * 查询某个特定的表
     */
    private final String SELECT_SINGLE_TABLE =
        "select table_name tableName, table_comment tableComment from information_schema.tables where table_schema = (select database()) and table_name = '%s'";

    /**
     * 查询某个特定的表的所有列
     */
    public static final String SELECT_TABLE_COLUMNS =
        "select column_name columnName, data_type dataType, column_comment columnComment,column_key columnKey, extra from information_schema.columns where table_name = '%s' and table_schema = (select database()) order by ordinal_position";

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
            ResultSet tableRs = DbUtils.executeQuery(connection, selectTablesSql);
            List<TableInfo> list = new ArrayList<>();
            TableInfo tableInfo = null;
            while (tableRs.next()) {
                tableInfo = new TableInfo();
                tableInfo.setTableName(tableRs.getString("tableName"));
                tableInfo.setTableComment(tableRs.getString("tableComment"));
                tableInfo.setEngine(tableRs.getString("engine"));
                tableInfo.setTableCollation(tableRs.getString("tableCollation"));
                tableInfo.setCreateTime(tableRs.getDate("createTime"));
                list.add(tableInfo);
            }
            /*
             * 返回分页结果
             */
            return new Page<>(total, list);
        }
    }

    @Override
    public byte[] generatorCode(CodegenerateVo vo) throws Exception {
        /*
         * 查询表信息
         */
        DataSource dataSource = DbUtils.dynamicCreateDatasource(vo.getTableQueryVo().getUrl(),
            vo.getTableQueryVo().getUsername(), vo.getTableQueryVo().getPassword());
        try (Connection connection = dataSource.getConnection()) {
            Set<String> tableNames = vo.getTableNames();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            if (!CollectionUtils.isEmpty(tableNames)) {
                for (String tableName : tableNames) {
                    /*
                     * 查询表信息
                     */
                    String selectTableSql = String.format(SELECT_SINGLE_TABLE, tableName);
                    ResultSet tableRs = DbUtils.executeQuery(connection, selectTableSql);
                    TableInfo tableInfo = null;
                    while (tableRs.next()) {
                        tableInfo = new TableInfo();
                        tableInfo.setTableName(tableRs.getString("tableName"));
                        tableInfo.setTableComment(tableRs.getString("tableComment"));
                    }
                    /*
                     * 查询列信息
                     */
                    String selectColumnsSql = String.format(SELECT_TABLE_COLUMNS, tableName);
                    ResultSet columnRs = DbUtils.executeQuery(connection, selectColumnsSql);
                    List<ColumnInfo> columnList = new ArrayList<>();
                    ColumnInfo columnInfo = null;
                    while (columnRs.next()) {
                        columnInfo = new ColumnInfo();
                        columnInfo.setColumnName(columnRs.getString("columnName"));
                        columnInfo.setDataType(columnRs.getString("dataType"));
                        columnInfo.setColumnComment(columnRs.getString("columnComment"));
                        columnInfo.setColumnKey(columnRs.getString("columnKey"));
                        columnInfo.setExtra(columnRs.getString("extra"));
                        columnList.add(columnInfo);
                    }
                    /*
                     * 代码生成
                     */

                    doGenerateCode(vo, tableInfo, columnList, zip);
                }
            }

            /*
             * 返回结果
             */
            return outputStream.toByteArray();
        }

    }

    /**
     * 生成代码
     * 
     * @param vo
     *            前端参数
     * @param tableInfo
     *            表信息
     * @param columnList
     *            列信息
     * @param zip
     *            ZipOutputStream
     */
    private void doGenerateCode(CodegenerateVo vo, TableInfo tableInfo, List<ColumnInfo> columnList,
        ZipOutputStream zip) {
        /*
         * 封装模板数据
         */
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableInfo.getTableName());
        map.put("pk", GenerateUtils.getPkColumn(columnList));
        map.put("className", StringUtils.isNotBlank(vo.getClassName()) ? vo.getClassName()
            : GenerateUtils.getClassName(tableInfo.getTableName()));
        map.put("classname", GenerateUtils.getClassname(map.get("className").toString()));
        map.put("columns", GenerateUtils.convertColumns(columnList));
        map.put("nowTime", DateUtils.getNowTime());
        map.put("author", StringUtils.isNotBlank(vo.getAuthor()) ? vo.getAuthor() : GenerateUtils.DEFAULT_AUTHOR);
        map.put("comments", StringUtils.isNotBlank(vo.getComments()) ? vo.getComments() : tableInfo.getTableComment());
        map.put("package",
            StringUtils.isNotBlank(vo.getPackageName()) ? vo.getPackageName() : GenerateUtils.DEFAULT_PACKAGENAME);

        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        VelocityContext context = new VelocityContext(map);

        /*
         * 获取模板列表
         */
        List<String> templates = GenerateUtils.getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                // 添加到zip
                String filePath =
                    GenerateUtils.getFilePath(template, map.get("className").toString(), map.get("package").toString());
                if (filePath == null) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn("获取完整文件路径失败，模板：{}", template);
                    }
                    continue;
                }
                zip.putNextEntry(new ZipEntry(filePath));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableInfo.getTableName(), e);
            }
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}表生成代码完成", tableInfo.getTableName());
        }

    }

}
