package com.ohj.maker;

//import com.ohj.maker.cli.CommandExecutor;

import com.ohj.maker.generator.main.MainGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Hipopaaaaa
 * @create 2023/12/18 20:32
 */


public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}