package com.ohj.maker.generator;

import cn.hutool.core.io.FileUtil;

import java.io.*;

/**
 * @author Hipopaaaaa
 * @create 2023/12/23 23:09
 * jar包生成器
 */
public class JarGenerator {
    public static void doGenerate(String projectDir) throws InterruptedException, IOException {
        if(!FileUtil.exist(projectDir)){
            System.out.println("文件不存在");
            return;
        }
        //windows系统要允许的命令
        String winMavenCommand="mvn.cmd clean package -DskipTests=true";
        //其他系统运行的命令
        String otherMavenCommand="mvn clean package -DskipTests=true";
        ProcessBuilder processBuilder;
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
             processBuilder = new ProcessBuilder(winMavenCommand.split(" "));
        }else {
             processBuilder = new ProcessBuilder(otherMavenCommand.split(" "));
        }

        processBuilder.directory(new File(projectDir));
        Process process = processBuilder.start();

        // 读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        while((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码："+exitCode);
    }

}
