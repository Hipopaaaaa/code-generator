package com.ohj.maker.generator.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Hipopaaaaa
 * @create 2024/1/9 22:41
 * 模型配置
 */
@Data
public class TemplateMakerModelConfig {
    private List<ModelInfoConfig> models ;

    private ModelGroupConfig modelGroupConfig;

    @Data
    @NoArgsConstructor
    public static class ModelInfoConfig{
        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String abbr;
        // 用于替换哪些文本
        private String replaceText;
    }

    @Data
    @NoArgsConstructor
    public static class ModelGroupConfig{
        private String condition;

        private String groupKey;

        private String groupName;
    }
}
