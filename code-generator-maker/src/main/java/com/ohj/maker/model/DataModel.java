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
     * 动态生成需求：
     * 1.在代码开头增加@Author 注释(增加代码)
     * 2.修改程序输出的信息提示(修改代码)
     * 3.将循环读取输入改为单词读取(可选代码)
     */

    /**
     * 作者
     */
    private String author = "Hipop";

    /**
     * 输出信息
     */
    private String outputText = "输出结果";

    /**
     * 是否循环
     */
    private boolean loop;
}
