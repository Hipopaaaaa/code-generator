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
     public static void doGenerate(Object model) throws TemplateException, IOException {

          String inputRootPath="/Users/hipopaaaa/Desktop/code-generator/code-generator/code-generator-demo-projects/acm-template-pro";
          String outputRootPath="/Users/hipopaaaa/Desktop/code-generator";

          String inputPath;
          String outputPath;
          inputPath=new File(inputRootPath,"src/main/resources/templates/MainTemplate.java.ftl").getAbsolutePath();
          outputPath=new File(outputRootPath,"src/com/ohj/acm/MainTemplate.java").getAbsolutePath();
          // 动态文件生成
          DynamicGenerator.doGenerate(inputPath,outputPath, model);

          inputPath=new File(inputRootPath,".gitignore").getAbsolutePath();
          outputPath=new File(outputRootPath,".gitignore").getAbsolutePath();
          // 静态文件生成
          StaticGenerator.copyFilesByRecursive(inputPath,outputPath);

          inputPath=new File(inputRootPath,"README.md").getAbsolutePath();
          outputPath=new File(outputRootPath,"README.md").getAbsolutePath();
          // 静态文件生成
          StaticGenerator.copyFilesByRecursive(inputPath,outputPath);

     }
}
