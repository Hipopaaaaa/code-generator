package com.ohj.maker.meta;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ohj.maker.meta.enums.FileGenerateTypeEnum;
import com.ohj.maker.meta.enums.FileTypeEnum;
import com.ohj.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Hipopaaaaa
 * @create 2024/1/2 19:38
 * meta文件的校验工具
 */
public class MetaValidator {

    public static void doValidAndFill(Meta meta) {
        validAndFillMetaRoot(meta);

        validAndFillFileConfig(meta);

        validAndFillModelConfig(meta);

    }

    private static void validAndFillModelConfig(Meta meta) {
        // modelConfig 校验和默认值
        Meta.ModelConfigDTO modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        List<Meta.ModelConfigDTO.ModelsDTO> modelInfoList = modelConfig.getModels();
        if (CollUtil.isEmpty(modelInfoList)) {
            return;
        }
        for (Meta.ModelConfigDTO.ModelsDTO modelInfo : modelInfoList) {
            String fieldName = modelInfo.getFieldName();
            if(StrUtil.isBlank(fieldName)){
                throw new MetaException("未填写 fieldName");
            }
            String modelInfoType = modelInfo.getType();
            if(StrUtil.isEmpty(modelInfoType)){
                modelInfo.setType(ModelTypeEnum.STRING.getValue());
            }
        }
    }

    private static void validAndFillFileConfig(Meta meta) {
        // fileConfig 校验和默认值
        Meta.FileConfigDTO fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        // sourceRootPath 必填
        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)) {
            throw new MetaException("未填写 sourceRootPath");
        }
        // inputRootPath: .source + sourceRootPath的最后一个层级路径
        String inputRootPath = fileConfig.getInputRootPath();
        if (StrUtil.isEmpty(inputRootPath)) {
            String defaultInputRootPath = ".source" + File.separator +
                    FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
            fileConfig.setInputRootPath(defaultInputRootPath);
        }
        // outputRootPath: 默认为当前路径下的 generated
        String outputRootPath = fileConfig.getOutputRootPath();
        if(StrUtil.isEmpty(outputRootPath)){
            fileConfig.setOutputRootPath("generated");
        }
        // fileConfigType: 默认dir
        String fileConfigType = fileConfig.getType();
        if(StrUtil.isEmpty(fileConfigType)){
            fileConfig.setType(FileTypeEnum.DIR.getValue());
        }

        List<Meta.FileConfigDTO.FileInfo> fileInfoList = fileConfig.getFiles();
        if (CollUtil.isEmpty(fileInfoList)) {
            return;
        }
        for (Meta.FileConfigDTO.FileInfo fileInfo : fileInfoList) {
            // inputPath: 必填
            String inputPath = fileInfo.getInputPath();
            if(StrUtil.isBlank(inputPath)){
                throw new MetaException("未填写 inputPath");
            }
            // outputPath: 默认为 inputPath
            String outputPath = fileInfo.getOutputPath();
            if(StrUtil.isEmpty(outputPath)){
                fileInfo.setOutputPath(inputPath);
            }
            // type: 默认 inputPath 有文件名后缀，默认为file，否则就是dir
            String type = fileInfo.getType();
            if(StrUtil.isBlank(type)){
                if(StrUtil.isBlank(FileUtil.getSuffix(inputPath))){
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                }else {
                    fileInfo.setType(FileTypeEnum.FILE.getValue());
                }
            }
            // generateType: 文件结尾不为 ftl,为 static，否则 dynamic
            String generateType = fileInfo.getGenerateType();
            if(StrUtil.isBlank(generateType)){
                if(inputPath.endsWith(".ftl")){
                    fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                }else {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                }
            }

        }

    }

    private static void validAndFillMetaRoot(Meta meta) {
        // 基础信息和默认值
        String name =StrUtil.blankToDefault(meta.getName(),"my-generator");
        String description = StrUtil.emptyToDefault(meta.getDescription(),"模版代码生成器");
        String basePackage = StrUtil.blankToDefault(meta.getBasePackage(),"com.Hipopaaaaa");
        String version = StrUtil.emptyToDefault(meta.getVersion(),"1.0");
        String author = StrUtil.emptyToDefault(meta.getAuthor(),"Hipopaaaaa");
        String createTime = StrUtil.emptyToDefault(meta.getCreateTime(),DateUtil.now());
        meta.setName(name);
        meta.setDescription(description);
        meta.setBasePackage(basePackage);
        meta.setVersion(version);
        meta.setAuthor(author);
        meta.setCreateTime(createTime);
    }
}
