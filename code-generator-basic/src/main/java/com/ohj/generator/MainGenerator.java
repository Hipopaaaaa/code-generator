package com.ohj.generator;

import com.ohj.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 21:06
 */
public class MainGenerator {
     public static void main(String[] args) throws TemplateException, IOException {
          // 静态文件生成
          String projectPath = System.getProperty("user.dir");
          System.out.println(projectPath);
          String inputPath = projectPath+ File.separator+ "code-generator-demo-projects" + File.separator + "acm-template";
          String outputPath = projectPath;
          StaticGenerator.copyFilesByRecursive(inputPath,outputPath);

          // 动态文件生成
          String dynamicInputPath = projectPath + File.separator + "code-generator-basic"+File.separator+"src/main/resources/templates/MainTemplate.java.ftl";
          String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/ohj/acm/MainTemplate.java";

          MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
          mainTemplateConfig.setAuthor("Hipop");
          mainTemplateConfig.setOutputText("求和结果：");
          mainTemplateConfig.setLoop(true);
          DynamicGenerator.doGenerate(dynamicInputPath,dynamicOutputPath, mainTemplateConfig);
     }
}
