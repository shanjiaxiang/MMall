package com.latte.example.generators;

import com.late.core.wechat.templates.WXPayEntryTemplate;
import com.latte.annotations.PayEntryGenerator;

/**
 * Created by Administrator on 2018\11\13 0013.
 */
@PayEntryGenerator(
        packageName = "com.latte.example",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {

    
}
