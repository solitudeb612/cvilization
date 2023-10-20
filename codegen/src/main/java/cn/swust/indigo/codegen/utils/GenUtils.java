package cn.swust.indigo.codegen.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.swust.indigo.codegen.entity.ColumnEntity;
import cn.swust.indigo.codegen.entity.GenConfig;
import cn.swust.indigo.codegen.entity.TableEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @date 2018-07-30
 */
@Slf4j @UtilityClass public class GenUtils {

    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String VO_JAVA_VM = "vo.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";
    private final String TEST_CON = "ControllerTest.java.vm";
    private final String TEST_MAPPER = "MapperTest.java.vm";
    private final String TEST_SERVICE = "ServiceImplTest.java.vm";

    private List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/Entity.java.vm");
        templates.add("templates/Mapper.java.vm");
        templates.add("templates/Mapper.xml.vm");
        templates.add("templates/Service.java.vm");
        templates.add("templates/ServiceImpl.java.vm");
        templates.add("templates/Controller.java.vm");
        templates.add("templates/vo.java.vm");
        templates.add("templates/test/ControllerTest.java.vm");
        templates.add("templates/test/MapperTest.java.vm");
        templates.add("templates/test/ServiceImplTest.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public void generatorCode(URL generatorFile, GenConfig genConfig, Map<String, String> table,
                              List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig(generatorFile);
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            tableEntity.setComments(genConfig.getComments());
        } else {
            tableEntity.setComments(table.get("tableComment"));
        }

        String tablePrefix;
        if (StrUtil.isNotBlank(genConfig.getTablePrefix())) {
            tablePrefix = genConfig.getTablePrefix();
        } else {
            tablePrefix = config.getString("tablePrefix");
        }

        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StringUtils.uncapitalize(className));
        //获取需要在swagger文档中隐藏的属性字段
        List<Object> hiddenColumns = config.getList("hiddenColumn");
        //列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));
            columnEntity.setNullable("NO".equals(column.get("isNullable")));
            columnEntity.setColumnType(column.get("columnType"));
            columnEntity.setSize(column.get("size") == null ? 0 : Long.parseLong(String.valueOf(column.get("size"))));
            //隐藏不需要的在接口文档中展示的字段
            if (hiddenColumns.contains(column.get("columnName"))) {
                columnEntity.setHidden(Boolean.TRUE);
            } else {
                columnEntity.setHidden(Boolean.FALSE);
            }
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            map.put("comments", genConfig.getComments());
        } else {
            map.put("comments", tableEntity.getComments());
        }

        if (StrUtil.isNotBlank(genConfig.getAuthor())) {
            map.put("author", genConfig.getAuthor());
        } else {
            map.put("author", config.getString("author"));
        }

        if (StrUtil.isNotBlank(genConfig.getModuleName())) {
            map.put("moduleName", genConfig.getModuleName());
        } else {
            map.put("moduleName", config.getString("moduleName"));
        }

        if (StrUtil.isNotBlank(genConfig.getPackageName())) {
            map.put("package", genConfig.getPackageName());
            map.put("mainPath", genConfig.getPackageName());
        } else {
            map.put("package", config.getString("package"));
            map.put("mainPath", config.getString("mainPath"));
        }
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(
                        getFileName(template, tableEntity.getCaseClassName(), map.get("package").toString(),
                                map.get("moduleName").toString()))));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
                IoUtil.close(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    /**
     * 列名转换成Java属性名
     */
    private String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private Configuration getConfig(URL generatorFile) {
        try {
            return new PropertiesConfiguration(generatorFile);
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath =
                File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        String testPath = File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            String packPath = packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
            packagePath += packPath;
            testPath += packPath;
        }

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "entity" + File.separator + "po" + File.separator + className + ".java";
        }

        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (template.contains(VO_JAVA_VM)) {
            return packagePath + "entity" + File.separator + "vo" + File.separator + "Q" + className + ".java";
        }

        if (template.contains(TEST_CON)) {
            return testPath + "controller" + File.separator + className + "ControllerTest.java";
        }

        if (template.contains(TEST_MAPPER)) {
            return testPath + "mapper" + File.separator + className + "MapperTest.java";
        }

        if (template.contains(TEST_SERVICE)) {
            return testPath + "service" + File.separator + "impl" + File.separator + className + "ServiceImplTest.java";
        }

        return null;
    }

}
