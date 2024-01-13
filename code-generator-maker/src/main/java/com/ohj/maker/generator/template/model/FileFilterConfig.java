package com.ohj.maker.generator.template.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Hipopaaaaa
 * @create 2024/1/9 22:39
 * 文件过滤规则
 */
@Data
@Builder
public class FileFilterConfig {
     /**
      * 过滤范围
      */
     private String range;
     /**
      * 过滤规则
      */
     private String rule;
     /**
      * 过滤值
      */
     private String value;
}
