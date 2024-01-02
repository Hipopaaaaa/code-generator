package com.ohj.maker.meta.enums;

import java.io.File;

/**
 * @author Hipopaaaaa
 * @create 2024/1/2 20:41
 * 文件类型枚举
 */
public enum FileTypeEnum {
   DIR("目录","dir"),FILE("文件","file");

   private final String text;
   private final String value;

     FileTypeEnum(String text, String value) {
          this.text = text;
          this.value = value;
     }

     public String getText() {
          return text;
     }

     public String getValue() {
          return value;
     }
}
