package com.tw;

import freemarker.template.Template;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerateUtils {
    private static final String COMPANY_NAME = PropertyUtil.getConfig("generator.company-name");

    private static final String PROJECT_NAME = PropertyUtil.getConfig("generator.project-name");
    private static final String PROJECT_PATH = PropertyUtil.getConfig("generator.project-path");
    private static final String TEST_PATH = PropertyUtil.getConfig("generator.test-path");
    //影响类名和变量名
    private static final String LOWER_TABLE_NAME_WITH_UNDERSCORE = PropertyUtil.getConfig("generator.po-name");
    //设置数据库链接的真实数据库名
    private static final String REAL_TABLE_NAME = PropertyUtil.getConfig("generator.table-name");
    private static final String URL = PropertyUtil.getConfig("datasource.url");
    private static final String USER = PropertyUtil.getConfig("datasource.username");
    private static final String PASSWORD = PropertyUtil.getConfig("datasource.password");
    private static final String CAPITAL_UPPER_TABLE_NAME = replaceUnderLineAndUpperCase(LOWER_TABLE_NAME_WITH_UNDERSCORE);

    private static final boolean ENABLE_PO_AND_DOMAIN = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.po-domain"));
    private static final boolean ENABLE_CONTROLLER = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.controller"));
    private static final boolean ENABLE_USE_CASE = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.use-case"));
    private static final boolean ENABLE_SERVICE = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.service"));
    private static final boolean ENABLE_REPO_INTERFACE = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.repo-interface"));
    private static final boolean ENABLE_JPA_REPO = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.jpa-repo"));
    private static final boolean ENABLE_REPO_IMPL = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.repo-impl"));
    private static final boolean ENABLE_TEST_BUILDER = Boolean.parseBoolean(PropertyUtil.getConfig("generator.enable.test-builder"));

    public static void main(String[] args) {
        CodeGenerateUtils.generate();
    }

    public static void generate() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet =
                    databaseMetaData.getColumns(null, "%", REAL_TABLE_NAME, "%");
            ResultSet builderResultSet = databaseMetaData.getColumns(null, "%", REAL_TABLE_NAME, "%");


            generatePOAndDomainFile(resultSet);
            generateControllerFile();
            generateUseCaseFile();
            generateServiceFile();
            generateRepositoryInterfaceFile();
            generateJpaRepositoryFile();
            generateRepositoryImplFile();
            generateTestBuilder(builderResultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateTestBuilder(ResultSet resultSet) throws Exception {
        if (!ENABLE_TEST_BUILDER) {
            return;
        }
        final String diskPath = TEST_PATH + "adapter/inbound/rest/resources/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/builder/";
        makeDirIfNotExists(diskPath);
        final String suffix = "TestBuilder.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "TestBuilder.ftl";
        File mapperFile = new File(path);

        List<ColumnClass> columnClassList = getModelColumns(resultSet);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("model_column", columnClassList);

        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void generatePOAndDomainFile(ResultSet resultSet) throws Exception {
        if (!ENABLE_PO_AND_DOMAIN) {
            return;
        }
        final String poDiskPath = PROJECT_PATH + "adapter/outbound/persistence/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(poDiskPath);
        final String poSuffix = "PO.java";
        final String poPath = poDiskPath + CAPITAL_UPPER_TABLE_NAME + poSuffix;
        final String poTemplateName = "PO.ftl";
        File poMapperFile = new File(poPath);

        final String domainDiskPath = PROJECT_PATH + "domain/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(domainDiskPath);
        final String domainSuffix = ".java";
        final String domainPath = domainDiskPath + CAPITAL_UPPER_TABLE_NAME + domainSuffix;
        final String domainTemplateName = "Domain.ftl";
        File domainMapperFile = new File(domainPath);

        List<ColumnClass> columnClassList = getModelColumns(resultSet);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("model_column", columnClassList);

        generateFileByTemplate(poTemplateName, poMapperFile, dataMap);
        generateFileByTemplate(domainTemplateName, domainMapperFile, dataMap);
    }

    private static List<ColumnClass> getModelColumns(ResultSet resultSet) throws SQLException {
        List<ColumnClass> columnClassList = new ArrayList<>();
        ColumnClass columnClass;
        while (resultSet.next()) {
            columnClass = new ColumnClass();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //转换字段名称，如 sys_name 变成 SysName
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }
        return columnClassList;
    }

    private static void generateControllerFile() throws Exception {
        if (!ENABLE_CONTROLLER) {
            return;
        }
        final String diskPath = PROJECT_PATH + "adapter/inbound/rest/resources/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(diskPath);
        final String suffix = "Controller.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "Controller.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void generateUseCaseFile() throws Exception {
        if (!ENABLE_USE_CASE) {
            return;
        }
        final String diskPath = PROJECT_PATH + "application/usecases/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(diskPath);
        final String suffix = "UseCase.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "UseCase.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void generateServiceFile() throws Exception {
        if (!ENABLE_SERVICE) {
            return;
        }
        final String diskPath = PROJECT_PATH + "domain/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(diskPath);
        final String suffix = "Service.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "Service.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void generateRepositoryInterfaceFile() throws Exception {
        if (!ENABLE_REPO_INTERFACE) {
            return;
        }
        final String diskPath = PROJECT_PATH + "domain/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(diskPath);
        final String suffix = "Repository.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "Repository.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void generateJpaRepositoryFile() throws Exception {
        if (!ENABLE_JPA_REPO) {
            return;
        }
        final String diskPath = PROJECT_PATH + "adapter/outbound/persistence/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(diskPath);
        final String suffix = "JpaRepository.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "JpaRepository.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void generateRepositoryImplFile() throws Exception {
        if (!ENABLE_REPO_IMPL) {
            return;
        }
        final String diskPath = PROJECT_PATH + "adapter/outbound/persistence/" + CAPITAL_UPPER_TABLE_NAME.toLowerCase() + "/";
        makeDirIfNotExists(diskPath);
        final String suffix = "RepositoryImpl.java";
        final String path = diskPath + CAPITAL_UPPER_TABLE_NAME + suffix;
        final String templateName = "RepositoryImpl.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private static void makeDirIfNotExists(String diskPath) {
        File dir = new File(diskPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static void generateFileByTemplate(final String templateName, File file, Map<String, Object> dataMap) throws Exception {
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("lower_table_name_with_underscore", LOWER_TABLE_NAME_WITH_UNDERSCORE);
        dataMap.put("capital_upper_table_name", CAPITAL_UPPER_TABLE_NAME);
        dataMap.put("company_name", COMPANY_NAME);
        dataMap.put("project_name", PROJECT_NAME);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
        template.process(dataMap, out);
    }

    public static String replaceUnderLineAndUpperCase(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return StringUtils.capitalize(result);
    }

}
