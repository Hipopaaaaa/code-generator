package com.ohj.maker.generator.file;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 21:06
 * 文件生成器
 */
public class FileGenerator {
     public static void doGenerate(Object model) throws TemplateException, IOException {
          // 静态文件生成
          String projectPath = System.getProperty("user.dir");
          File parentFile = new File(projectPath).getParentFile();
          String inputPath = new File(parentFile,"code-generator-demo-projects" + File.separator + "acm-template").getAbsolutePath();
          String outputPath = projectPath;
          StaticFileGenerator.copyFilesByHutool(inputPath,outputPath);

          // 动态文件生成
          String dynamicInputPath = projectPath +File.separator+"src/main/resources/templates/MainTemplate.java.ftl";
          String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/ohj/acm/MainTemplate.java";

          DynamicFileGenerator.doGenerate(dynamicInputPath,dynamicOutputPath, model);
     }
}
