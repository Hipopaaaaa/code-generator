package com.ohj.maker.generator.file;

import cn.hutool.core.io.FileUtil;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 18:31
 * 静态文件生成器
 */
public class StaticFileGenerator {

    /**
     * 拷贝文件
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFilesByHutool(String inputPath,String outputPath){
        // todo 这里有问题，复制不到.ignore文件(后期再修改)
        FileUtil.copy(inputPath,outputPath,false);
    }


}
