package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * @author ${author}
 * generate 命令：生成代码模版
 */
@Data
@Command(name = "generate",mixinStandardHelpOptions = true)
public class GenerateCommand implements Callable {
<#list modelConfig.models as modelInfo>

    <#if modelInfo.description??>
    /**
    * ${modelInfo.description}
    */
    </#if>
    @Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}", </#if>"--${modelInfo.fieldName}"},<#if modelInfo.description??>description = "${modelInfo.description}"</#if>,arity = "0..1",interactive = true,echo = true)
    private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
</#list>


    @Override
    public Object call() throws Exception {
        DataModel mainTemplateConfig = new DataModel();
        BeanUtil.copyProperties(this,mainTemplateConfig);
        MainGenerator.doGenerate(mainTemplateConfig);
        return 0;
    }
}
