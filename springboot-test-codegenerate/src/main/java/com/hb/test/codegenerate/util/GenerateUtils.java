package com.hb.test.codegenerate.util;

import com.hb.test.codegenerate.entity.ColumnInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成工具类
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
public class GenerateUtils {

    /**
     * 默认作者
     */
    public static final String DEFAULT_AUTHOR = "Mr.Huang";

    /**
     * 默认包名
     */
    public static final String DEFAULT_PACKAGENAME = "com.hb.test.codegenerate";

    /**
     * 数据库字段和实体类字段映射关系
     */
    private static Map<String, String> javaTypeMap = new HashMap<>();

    /**
     * 数据库字段类型
     */
    private static Map<String, String> jdbcTypeMap = new HashMap<>();

    static {
        initJavaTypeMap();
        initJdbcTypeMap();
    }

    /**
     * 初始化javaTypeMap
     */
    private static void initJavaTypeMap() {
        // 整数类型
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("number", "Integer");
        javaTypeMap.put("integer", "Integer");
        // 其他数值类型
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        // 字符串类型
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("tinyblob", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("blob", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumblob", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longblob", "String");
        javaTypeMap.put("longtext", "String");
        // 日期类型
        javaTypeMap.put("year", "Date");
        javaTypeMap.put("time", "Date");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
    }

    /**
     * 初始化jdbcTypeMap
     */
    private static void initJdbcTypeMap() {
        jdbcTypeMap.put("int", "INTEGER");
    }

    /**
     * 获取mybatis的xml文件里resultMap标签里的jdbcType
     * 
     * @param dataType
     *            mysql里的数据类型
     * @return jdbcType
     */
    public static String getJdbcType(String dataType) {
        String jdbcType = jdbcTypeMap.get(dataType);
        if (jdbcType == null) {
            jdbcType = dataType.toUpperCase();
        }
        return jdbcType;
    }

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> convertColumns(List<ColumnInfo> columns) {
        for (ColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StrUtils.underlineToHump(column.getColumnName());
            column.setLowerAttrName(StringUtils.uncapitalize(attrName));
            column.setUpperAttrName(attrName.substring(0, 1).toUpperCase() + attrName.substring(1));
            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType().toLowerCase());
            column.setAttrType(attrType);
        }
        return columns;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("vmtemplate/Mapper.xml.vm");
        templates.add("vmtemplate/Entity.java.vm");
        templates.add("vmtemplate/Mapper.java.vm");
        templates.add("vmtemplate/Service.java.vm");
        templates.add("vmtemplate/ServiceImpl.java.vm");
        templates.add("vmtemplate/Controller.java.vm");
        templates.add("vmtemplate/Api.js.vm");
        templates.add("vmtemplate/Page.vue.vm");
        return templates;
    }

    /**
     * 获取文件完整路径名
     */
    public static String getFilePath(String template, String upperClassName, String packageName) {

        String javaPath = getPackagePath(packageName);

        if (template.contains("Mapper.xml.vm")) {
            return javaPath + upperClassName + "Mapper.xml";
        }

        if (template.contains("Entity.java.vm")) {
            return javaPath + upperClassName + "DO.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return javaPath + "I" + upperClassName + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return javaPath + "I" + upperClassName + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return javaPath + upperClassName + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return javaPath + upperClassName + "Controller.java";
        }

        if (template.contains("Api.js.vm")) {
            return javaPath + upperClassName.toLowerCase() + ".js";
        }

        if (template.contains("Page.vue.vm")) {
            return javaPath + upperClassName.toLowerCase() + ".vue";
        }

        return null;
    }

    /**
     * 获取包路径
     * 
     * @param packageName
     *            包名
     * @return 包路径
     */
    public static String getPackagePath(String packageName) {
        return new StringBuilder().append(packageName.replace(".", "/")).append("/").toString();
    }

    /**
     * 获取主键列信息
     * 
     * @param columnList
     *            表下所有列
     * @return 主键列
     */
    public static ColumnInfo getPkColumn(List<ColumnInfo> columnList) {
        return columnList.stream().filter(columnInfo -> "PRI".equalsIgnoreCase(columnInfo.getColumnKey())).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("查找主键列信息失败"));
    }

    /**
     * 通过表名获取类名，首字母是大写的
     * 
     * @param tableName
     *            表名
     * @return 类名
     */
    public static String getUpperClassName(String tableName) {
        String str = StrUtils.underlineToHump(tableName);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 通过表名获取类名，首字母是小写的
     *
     * @param className
     *            首字母大写的类名
     * 
     * @return 类名
     */
    public static String getLowerClassName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

}
