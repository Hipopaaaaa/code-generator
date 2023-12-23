package com.ohj.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.ohj.maker.generator.file.DynamicFileGenerator;
import com.ohj.maker.meta.Meta;
import com.ohj.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author Hipopaaaaa
 * @create 2023/12/23 20:15
 */
public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        // 输出的根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath+ File.separator+"generated";
        if(!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }

        // 读取 resources 目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();

        // Java包的基础路径 com/ohj
        String outputBasePackage= meta.getBasePackage();
        String outputBasePackagePath= StrUtil.join(File.separator,StrUtil.split(outputBasePackage,"."));
        // 生成 generated/src/main/java/com/ohj/xxx
        String outputBaseJavaPackagePath = outputPath+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+outputBasePackagePath;

        // 生成model(model.DataModel)
        String inputFilePath;
        String outputFilePath;
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"model"+File.separator+"DataModel.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"model"+File.separator+"DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成指令类(cli.command.GenerateCommand)
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"command"+File.separator+"GenerateCommand.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"command"+File.separator+"GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);
        // 生成指令类(cli.command.ConfigCommand)
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"command"+File.separator+"ConfigCommand.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"command"+File.separator+"ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);
        // 生成指令类(cli.command.ListCommand)
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"command"+File.separator+"ListCommand.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"command"+File.separator+"ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成指令生成器(cli.CommandExecutor)
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"cli"+File.separator+"CommandExecutor.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"cli"+File.separator+"CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成Main
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"Main.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成动态文件生成器
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"generator"+File.separator+"DynamicGenerator.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"generator"+File.separator+"DynamicGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成静态文件生成器
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"generator"+File.separator+"StaticGenerator.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"generator"+File.separator+"StaticGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成核心生成器
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"java"+File.separator+"generator"+File.separator+"MainGenerator.java.ftl";
        outputFilePath=outputBaseJavaPackagePath+File.separator+"generator"+File.separator+"MainGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        // 生成pom.xml
        inputFilePath=inputResourcePath+File.separator+"templates"+File.separator+"pom.xml.ftl";
        outputFilePath=outputPath+File.separator+"pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //构建jar包
        JarGenerator.doGenerate(outputPath);

        // 封装脚本
        String shellOutputFilePath = outputPath+File.separator+"generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar",meta.getName(),meta.getVersion());
        String jarPath ="target"+File.separator+jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath,jarPath);

    }
}
