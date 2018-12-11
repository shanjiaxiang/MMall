package com.late.core.ui.recycler;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\11\15 0015.
 */

public abstract class DataConverter  {
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList <>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    public String getJsonData(){
        if (mJsonData == null && mJsonData.isEmpty()){
            throw new NullPointerException("JsonData is null ...");
        }
        return mJsonData;
    }

    public void clearData(){
        ENTITIES.clear();
    }

}






