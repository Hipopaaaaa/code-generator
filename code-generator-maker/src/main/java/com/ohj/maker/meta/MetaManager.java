package com.ohj.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author Hipopaaaaa
 * @create 2023/12/23 20:00
 * 把meta.json的数据映射到Meta
 */
public class MetaManager {

    private static volatile Meta meta;

    public static Meta getMetaObject() {
        if (meta == null) {
            synchronized (MetaManager.class) {
                if (meta == null) {
                   meta=initMeta();
                }
            }
        }
        return meta;
    }

    public static Meta initMeta(){
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta meta = JSONUtil.toBean(metaJson, Meta.class);
        // todo 校验配置文件，处理默认值
        return meta;
    }

}
