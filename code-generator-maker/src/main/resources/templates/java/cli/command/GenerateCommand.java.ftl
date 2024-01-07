package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

<#--生成选项-->
<#macro generateOption indent modelInfo>
<#if modelInfo.description??>
${indent}/**
${indent} * ${modelInfo.description}
${indent} */
</#if>
${indent}@Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}", </#if>"--${modelInfo.fieldName}"},<#if modelInfo.description??>description = "${modelInfo.description}"</#if>,arity = "0..1",interactive = true,echo = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
</#macro>

<#macro  generateCommand indent modelInfo>
${indent}System.out.println("输入${modelInfo.groupName}配置：");
${indent}CommandLine commandLine = new CommandLine(${modelInfo.type}Command.class);
${indent}commandLine.execute(${modelInfo.allArgsStr});
</#macro>


/**
 * @author ${author}
 * generate 命令：生成代码模版
 */
@Data
@Command(name = "generate", description="生成代码", mixinStandardHelpOptions = true)
public class GenerateCommand implements Callable {
<#list modelConfig.models as modelInfo>
    <#-- 有分组-->
    <#if modelInfo.groupKey??>
    /**
     * ${modelInfo.groupName}
     */
    static DataModel.${modelInfo.type} ${modelInfo.groupKey} = new DataModel.${modelInfo.type}();

    @Command(name = "${modelInfo.groupName!}", description = "${modelInfo.discription!}")
    @Data
    public static class ${modelInfo.type}Command implements Runnable{

        <#list modelInfo.models as subModelInfo>
            <@generateOption indent="        " modelInfo=subModelInfo/>
        </#list>

        @Override
        public void run() {
            <#list modelInfo.models as subModelInfo>
            ${modelInfo.groupKey}.${subModelInfo.fieldName} = ${subModelInfo.fieldName};
            </#list>
        }
     }
    <#else>
        <@generateOption indent="    " modelInfo=modelInfo/>
    </#if>
</#list>


    <#-- 生成调用方法-->
    @Override
    public Object call() throws Exception {
        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        <#if modelInfo.condition??>
        if(${modelInfo.condition}){
            <@generateCommand indent="            " modelInfo=modelInfo/>
        }
        <#else>
           <@generateCommand indent="        " modelInfo=modelInfo/>
        </#if>
        </#if>
        </#list>
        <#--填充数据模型对象-->
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this,dataModel);
        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        dataModel.${modelInfo.groupKey} = ${modelInfo.groupKey};
        </#if>
        </#list>
        MainGenerator.doGenerate(dataModel);
        return 0;
    }
}
