package com.ohj.maker.generator.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.ohj.maker.generator.template.enums.FileFilterRangeEnum;
import com.ohj.maker.generator.template.enums.FileFilterRuleEnum;
import com.ohj.maker.generator.template.model.FileFilterConfig;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hipopaaaaa
 * @create 2024/1/9 23:03
 * 文件过滤器
 */
public class FileFilter {


    /**
     * 对某个文件或目录进行过滤，返回文件列表
     * @param filePath 路径
     * @param fileFilterConfigList 过滤规则列表
     * @return
     */
    public static List<File> doFilter(String filePath,List<FileFilterConfig> fileFilterConfigList){
        //根据路径获取所有文件
        List<File> fileList = FileUtil.loopFiles(filePath);
        return fileList.stream().filter(file -> doSingleFileFilter(fileFilterConfigList,file)).collect(Collectors.toList());
    }


    /**
     * 单个文件过滤
     * @param fileFilterConfigList 文件过滤配置列表
     * @param file 文件
     * @return
     */
    public static boolean doSingleFileFilter(List<FileFilterConfig> fileFilterConfigList, File file){
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        // 所有过滤器校验结束后的结果
        boolean result=true;

        if(CollUtil.isEmpty(fileFilterConfigList)){
            return true;
        }
        // 遍历过滤条件
        for (FileFilterConfig fileFilterConfig : fileFilterConfigList) {
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();

            FileFilterRangeEnum fileFilterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            if(fileFilterRangeEnum==null){
                continue;
            }
            // 要过滤的原内容(根据范围做一个统一)
            String content = fileName;
            switch (fileFilterRangeEnum){
                case FILE_NAME:
                    content=fileName;
                    break;
                case FILE_CONTENT:
                    content=fileContent;
                    break;
                default:
            }
            FileFilterRuleEnum fileFilterRuleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if(fileFilterRuleEnum==null){
                continue;
            }
            switch (fileFilterRuleEnum){
                case CONTAINS:
                    result=content.contains(value);
                    break;
                case STARTS_WITH:
                    result=content.startsWith(value);
                    break;
                case ENDS_WITH:
                    result=content.endsWith(value);
                    break;
                case REGEX:
                    result=content.matches(value);
                    break;
                case EQUALS:
                    result=content.equals(value);
                    break;
                default:
            }
            // 文件只要不满足一个过滤条件就返回
            if(!result){
                return false;
            }
        }
        //都满足
        return true;
    }

}
