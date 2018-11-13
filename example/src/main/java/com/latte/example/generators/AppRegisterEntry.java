package com.latte.example.generators;

import com.late.core.wechat.templates.AppRegisterTemplate;
import com.latte.annotations.AppRegisterGenerator;

/**
 * Created by Administrator on 2018\11\13 0013.
 */
@AppRegisterGenerator(
        packageName = "com.diabin.fastec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegisterEntry {

    
}
