package com.ohj.maker.generator.main;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Hipopaaaaa
 * @create 2023/12/23 20:15
 */
public class MainGenerator extends GenerateTemplate {
    @Override
    protected void buildDist(String outputPath, String shellOutputFilePath, String jarPath, String sourceCopyDestPath) {
        System.out.println("不要输出 dist");
    }

}
