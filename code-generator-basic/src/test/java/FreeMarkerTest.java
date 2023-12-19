import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hipopaaaaa
 * @create 2023/12/19 19:26
 */
public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模版文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置模版文件的字符集
        configuration.setDefaultEncoding("utf-8");
        // 数字格式化
        configuration.setNumberFormat("0.######");
        // 创建模版对象，加载指定模版
        Template template = configuration.getTemplate("myweb.html.ftl");

        // 数据模型
        HashMap<Object, Object> dataModel = new HashMap<>();
        dataModel.put("currentYear",2023);
        ArrayList<Map<String, Object>> menuItems = new ArrayList<>();
        HashMap<String, Object> menuItem1 = new HashMap<>();
        menuItem1.put("url","https://codefather.cn");
        menuItem1.put("label","编程导航");
        HashMap<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("url","https://laoyujianli.com");
        menuItem2.put("label","老鱼简历");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        dataModel.put("menuItems",menuItems);

        // 指定的生成文件
        FileWriter out = new FileWriter("myweb.html");

        // 生成文件
        template.process(dataModel,out);
        out.close();

    }
}
