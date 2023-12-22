package com.ohj.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.ohj.model.MainTemplateConfig;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;

/**
 * @author Hipopaaaaa
 * @create 2023/12/22 20:03
 *  config 命令：输出允许用户传入的动态参数的信息
 */
@Command(name = "config",mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(MainTemplateConfig.class);
        for (Field field : fields) {
            System.out.println("字段类型："+field.getType());
            System.out.println("字段名称："+field.getName());
        }
    }
}
