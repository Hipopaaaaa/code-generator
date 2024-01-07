package com.ohj.maker.model;

import lombok.Data;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 20:14
 * 静态模版配置
 */
@Data
public class DataModel {

    /**
     * 是否生成.gitignore文件
     */
    public boolean needGit =true;

    /**
     * 作者
     */
    public String author = "Hipop";

    /**
     * 输出信息
     */
    public String outputText = "输出结果";

    /**
     * 是否循环
     */
    public boolean loop;
}
