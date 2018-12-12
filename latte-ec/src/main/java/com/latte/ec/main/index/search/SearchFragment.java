package com.latte.ec.main.index.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.late.core.fragments.LatteFragment;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.late.core.util.log.LatteLogger;
import com.late.core.util.storage.LattePreference;
import com.latte.ec.R;
import com.latte.ec.main.sort.content.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends LatteFragment {
    private SearchAdapter adapter;
    private AppCompatEditText mSearchEdit = null;

    void onClickSearch(){
        toggleInput(getContext());
        String searchItemText = mSearchEdit.getText().toString();
        saveItem(searchItemText);
    }

    private void saveItem(String item){
        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)){
            List<String> history;
            final String historyStr = LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if (StringUtils.isEmpty(historyStr)){
                history = new ArrayList<>();
            }else {
                LatteLogger.d("search","historyStr:" +historyStr);
                history = JSON.parseObject(historyStr, ArrayList.class);
                LatteLogger.d("search", " history"+history);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);
            LatteLogger.d("search", " history toJSONString:"+history);
            LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY, json);
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final RecyclerView recyclerView = $(R.id.rv_search);
        mSearchEdit = $(R.id.et_search_view);
        $(R.id.tv_top_search).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickSearch();
            }
        });

        $(R.id.icon_top_search_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });


        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        adapter = new SearchAdapter(data);
        recyclerView.setAdapter(adapter);

        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20,20)
                        .color(Color.GRAY)
                        .build();
            }
        });
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void toggleInput(Context context){
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }

    @Override
    public void onPause() {
        toggleInput(getContext());
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
