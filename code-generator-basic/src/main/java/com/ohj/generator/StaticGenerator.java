package com.ohj.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 18:31
 * 静态文件生成器
 */
public class StaticGenerator {

    /**
     * 拷贝文件
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFilesByHutool(String inputPath,String outputPath){
        FileUtil.copy(inputPath,outputPath,false);
    }

    /**
     * 递归拷贝文件
     * @param inputPath
     * @param outputPath
     */
    public static void copyFilesByRecursive(String inputPath,String outputPath){
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFilesByRecursive(inputFile,outputFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  核心思路： 先创建目录，然后遍历目录内的文件，依次复制
     * @param inputPath
     * @param outputPath
     */
    public static void copyFilesByRecursive(File inputPath,File outputPath) throws IOException {
        // 区分是文件还是目录
        if(inputPath.isDirectory()){
            File destOutputFile = new File(outputPath, inputPath.getName());
            // 如果是目录，先创建目标目录
            if(!destOutputFile.exists()){
                destOutputFile.mkdirs();
            }
            // 获取目录下的所有文件和子目录
            File[] files = inputPath.listFiles();
            // 无子文件，直接结束
            if(ArrayUtil.isEmpty(files)){
                return;
            }
            for (File file : files) {
                //递归拷贝下一层文件
                copyFilesByRecursive(file,destOutputFile);
            }
        }else {
            // 是文件，直接复制到目标目录下
            Path destPath = outputPath.toPath().resolve(inputPath.getName());
            Files.copy(inputPath.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
