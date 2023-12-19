package com.ohj.generator;

import com.ohj.Main;
import com.ohj.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 20:24
 * 动态文件生成器
 */
public class DynamicGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        String projectPath = System.getProperty("user.dir")+File.separator+"code-generator-basic";
        String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputPath = projectPath + File.separator + "MainTemplate.java";

        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("ohj");
        mainTemplateConfig.setOutputText("求和结果：");
        mainTemplateConfig.setLoop(false);
        doGenerate(inputPath,outputPath, mainTemplateConfig);
    }

    /**
     * 生成文件
     * @param inputPath 模版文件输入路径
     * @param outputPath 输出路径
     * @param object 数据模型
     * @throws IOException
     * @throws TemplateException
     */
    public static void doGenerate(String inputPath, String outputPath, Object object) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模版文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        // 设置模版文件的字符集
        configuration.setDefaultEncoding("utf-8");
        // 数字格式化
        configuration.setNumberFormat("0.######");
        // 创建模版对象，加载指定模版
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);


        // 指定的生成文件
        FileWriter out = new FileWriter(outputPath);

        // 生成文件
        template.process(object,out);
        out.close();
    }
}
