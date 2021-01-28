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

    // 默认作者
    public static final String DEFAULT_AUTHOR = "Mr.Huang";
    // 默认包名
    public static final String DEFAULT_PACKAGENAME = "com.hb.test.codegenerate";
    // 默认表前缀
    public static final String DEFAULT_TABLEPREFIX = "t_";

    // 类型转换
    public static Map<String, String> javaTypeMap = new HashMap<>();

    static {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("number", "Integer");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("varchar2", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("time", "Date");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
    }

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> convertColumns(List<ColumnInfo> columns) {
        for (ColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StrUtils.underlineToHump(column.getColumnName());
            column.setAttrName(StringUtils.uncapitalize(attrName));
            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType());
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
        List<String> templates = new ArrayList<String>();
        templates.add("vm/Entity.java.vm");
        // templates.add("vm/Mapper.java.vm");
        // templates.add("vm/Service.java.vm");
        // templates.add("vm/ServiceImpl.java.vm");
        // templates.add("vm/Controller.java.vm");
        // templates.add("vm/Mapper.xml.vm");
        // templates.add("vm/api.js.vm");
        return templates;
    }

    /**
     * 获取文件完整路径名
     */
    public static String getFilePath(String template, String className, String packageName) {

        String javaPath = getProjectPath(packageName);

        if (template.contains("Entity.java.vm")) {
            return javaPath + className + "DO.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return javaPath + "I" + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return javaPath + "I" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return javaPath + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return javaPath + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return javaPath + className + "Mapper.xml";
        }

        return null;
    }

    /**
     * 获取工程路径
     * 
     * @param packageName
     *            包名
     * @return 工程路径
     */
    public static String getProjectPath(String packageName) {
        return new StringBuilder().append("src/main/java/").append(packageName.replace(".", "/")).append("/")
            .toString();
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
    public static String getClassName(String tableName) {
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
    public static String getClassname(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

}
