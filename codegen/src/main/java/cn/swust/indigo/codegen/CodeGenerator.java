package cn.swust.indigo.codegen;

import cn.hutool.core.io.IoUtil;

import cn.swust.indigo.codegen.dao.SysGeneratorMapper;

import cn.swust.indigo.codegen.entity.GenConfig;
import cn.swust.indigo.codegen.utils.GenUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class CodeGenerator {
    public static void main(String[] args) throws FileNotFoundException {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.codeGenerator();
    }

    public void codeGenerator() throws FileNotFoundException {
        Map<String, String> table = new HashMap<String, String>();
        table.put("tableName", "task_commit");
        table.put("tableComment", "在线申报提交要求");
        List<Map<String, String>> columns;

        InputStream inputStream = this.getClass().getClassLoader().
                getResourceAsStream("mybatis-config.xml");
        URL generatorFile = this.getClass().getClassLoader().getResource("generator.properties");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SysGeneratorMapper mapper = session.getMapper(SysGeneratorMapper.class);
            columns = mapper.queryColumns(table.get("tableName"));

        } catch (Exception exception) {
            System.out.println(exception.toString());
            System.out.println("数据访访问失败");
            return;
        } finally {
            session.close();
        }

        GenConfig genConfig = new GenConfig("cn.swust.indigo",
                "lhz", "mce", "", table.get("tableName"),
                table.get("tableComment"));

        File zipFile = new File(table.get("tableName") + ".zip");
        OutputStream outputStream = new FileOutputStream(zipFile);
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        //生成代码
        GenUtils.generatorCode(generatorFile, genConfig, table, columns, zip);
        IoUtil.close(zip);
    }
}
