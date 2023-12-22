package com.ohj.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

/**
 * @author Hipopaaaaa
 * @create 2023/12/22 20:04
 * list 命令 :输出acm-template目录的所有内容
 */
@Command(name = "list",mixinStandardHelpOptions = true)
public class ListCommand implements Runnable{

    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        String inputPath = new File(parentFile, "code-generator-demo-projects" + File.separator + "acm-template").getAbsolutePath();
        List<File> files = FileUtil.loopFiles(inputPath);
        files.forEach(System.out::println);
    }
}
