package ${basePackage}.generator;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author ${author}
 * 核心生成器
 */
public class MainGenerator {
     public static void doGenerate(Object model) throws TemplateException, IOException {

          String inputRootPath="${fileConfig.inputRootPath}";
          String outputRootPath="${fileConfig.outputRootPath}";

          String inputPath;
          String outputPath;
     <#list fileConfig.files as fileInfo>

          inputPath=new File(inputRootPath,"${fileInfo.inputPath}").getAbsolutePath();
          outputPath=new File(outputRootPath,"${fileInfo.outputPath}").getAbsolutePath();
          <#if fileInfo.generateType == "static">
          // 静态文件生成
          StaticGenerator.copyFilesByHutool(inputPath,outputPath);
          <#else>
          // 动态文件生成
          DynamicGenerator.doGenerate(inputPath,outputPath, model);
          </#if>
     </#list>
     }
}
