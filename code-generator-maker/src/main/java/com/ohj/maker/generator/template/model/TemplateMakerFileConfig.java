package com.ohj.maker.generator.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Hipopaaaaa
 * @create 2024/1/9 22:41
 * 模版文件配置
 */
@Data
public class TemplateMakerFileConfig {
    private List<FileInfoConfig> files;

    private FileGroupConfig fileGroupConfig;

    @Data
    @NoArgsConstructor
    public static class FileInfoConfig{
        private String path;
        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    @NoArgsConstructor
    public static class FileGroupConfig{
        private String condition;

        private String groupKey;

        private String groupName;
    }
}
