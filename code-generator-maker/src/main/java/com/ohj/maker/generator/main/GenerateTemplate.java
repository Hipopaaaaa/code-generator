package com.ohj.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.ohj.maker.generator.JarGenerator;
import com.ohj.maker.generator.ScriptGenerator;
import com.ohj.maker.generator.file.DynamicFileGenerator;
import com.ohj.maker.meta.Meta;
import com.ohj.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author Hipopaaaaa
 * @create 2024/1/2 21:01
 */
public abstract class GenerateTemplate {

     public void doGenerate() throws TemplateException, IOException, InterruptedException{
          Meta meta = MetaManager.getMetaObject();
          System.out.println(meta);

          // 输出的根路径
          String projectPath = System.getProperty("user.dir");
          String outputPath = projectPath+ File.separator+"generated"+ File.separator+meta.getName();
          if(!FileUtil.exist(outputPath)){
               FileUtil.mkdir(outputPath);
          }
          // 1、复制原始文件
          String sourceCopyDestPath = copySource(meta, outputPath);

          // 2、代码生成
          generateCode(meta, outputPath);

          // 3、构建jar包
          String jarPath = buildJar(outputPath, meta);

          // 4、封装脚本
          String shellOutputFilePath= buildScript(outputPath, jarPath);

          // 5、生成精简版的程序
          buildDist(outputPath, shellOutputFilePath,jarPath, sourceCopyDestPath);
     }

     protected  void buildDist(String outputPath, String shellOutputFilePath,String jarPath, String sourceCopyDestPath) {
          String distOutputPath= outputPath +"-dist";
          // 拷贝jar包
          String targetAbsolutePath = distOutputPath+File.separator+"target";
          FileUtil.mkdir(targetAbsolutePath);
          String jarAbsolutePath = outputPath +File.separator+ jarPath;
          FileUtil.copy(jarAbsolutePath,targetAbsolutePath,true);
          // 拷贝脚本文件
          FileUtil.copy(shellOutputFilePath,distOutputPath,true);
          FileUtil.copy(shellOutputFilePath +".bat",distOutputPath,true);
          // 拷贝源模版文件
          FileUtil.copy(sourceCopyDestPath,distOutputPath,true);
     }

     protected  String buildScript(String outputPath, String jarPath) {
          String shellOutputFilePath = outputPath +File.separator+"generator";
          ScriptGenerator.doGenerate(shellOutputFilePath,jarPath);
          return shellOutputFilePath;
     }


     protected  String buildJar(String outputPath,Meta meta) throws InterruptedException, IOException {
          JarGenerator.doGenerate(outputPath);
          String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
          String jarPath ="target"+File.separator+jarName;
          return jarPath;
     }

     protected  void generateCode(Meta meta, String outputPath) throws IOException, TemplateException {
          // 读取 resources 目录
          ClassPathResource classPathResource = new ClassPathResource("");
          String inputResourcePath = classPathResource.getAbsolutePath();

          // Java包的基础路径 com/ohj
          String outputBasePackage= meta.getBasePackage();
          String outputBasePackagePath= StrUtil.join(File.separator,StrUtil.split(outputBasePackage,"."));
          // 生成 generated/src/main/java/com/ohj/xxx
          String outputBaseJavaPackagePath = outputPath +File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+outputBasePackagePath;

          // 生成model(model.DataModel)
          String inputFilePath;
          String outputFilePath;
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"model"+File.separator+"DataModel.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"model"+File.separator+"DataModel.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成指令类(cli.command.GenerateCommand)
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"command"+File.separator+"GenerateCommand.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"command"+File.separator+"GenerateCommand.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);
          // 生成指令类(cli.command.ConfigCommand)
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"command"+File.separator+"ConfigCommand.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"command"+File.separator+"ConfigCommand.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);
          // 生成指令类(cli.command.ListCommand)
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"command"+File.separator+"ListCommand.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"command"+File.separator+"ListCommand.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成指令生成器(cli.CommandExecutor)
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"CommandExecutor.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"CommandExecutor.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成Main
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"Main.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"Main.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成动态文件生成器
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"generator"+File.separator+"DynamicGenerator.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"generator"+File.separator+"DynamicGenerator.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成静态文件生成器
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"generator"+File.separator+"StaticGenerator.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"generator"+File.separator+"StaticGenerator.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成核心生成器
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"generator"+File.separator+"MainGenerator.java.ftl";
          outputFilePath=outputBaseJavaPackagePath+File.separator+"generator"+File.separator+"MainGenerator.java";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成pom.xml
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"pom.xml.ftl";
          outputFilePath= outputPath +File.separator+"pom.xml";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);

          // 生成README.md
          inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"README.md.ftl";
          outputFilePath= outputPath +File.separator+"README.md";
          DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath, meta);
     }

     protected  String copySource(Meta meta, String outputPath) {
          //从原始模版文件路径复制到生成到代码包中
          String sourceRootPath = meta.getFileConfig().getSourceRootPath();
          String sourceCopyDestPath = outputPath +File.separator+".source";
          FileUtil.copy(sourceRootPath,sourceCopyDestPath,false);
          return sourceCopyDestPath;
     }
}
