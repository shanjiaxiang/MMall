package com.latte.example.generators;

import com.late.core.wechat.templates.WXEntryTemplate;
import com.latte.annotations.EntryGenerator;

/**
 * Created by Administrator on 2018\11\13 0013.
 */
@EntryGenerator(
        packageName = "com.diabin.fastec.example",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {


}
