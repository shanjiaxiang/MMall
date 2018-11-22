package com.latte.ec.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.late.core.fragments.LatteFragment;

/**
 * Created by Administrator on 2018\11\22 0022.
 */

public class ListBean implements MultiItemEntity {

    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private LatteFragment mFragment = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;


    public ListBean(int mItemType,
                    String mImageUrl,
                    String mText,
                    String mValue,
                    int mId,
                    LatteFragment mFragment,
                    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mText = mText;
        this.mValue = mValue;
        this.mId = mId;
        this.mFragment = mFragment;
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmText() {
        if (mText == null){
            mText = "";
        }
        return mText;
    }

    public String getmValue() {
        if (mValue == null){
            mValue = "";
        }
        return mValue;
    }

    public int getmId() {
        return mId;
    }

    public LatteFragment getmFragment() {
        return mFragment;
    }

    public CompoundButton.OnCheckedChangeListener getmOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int id = 0;
        private int itemType = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private LatteFragment fragment = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setFragment(LatteFragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public ListBean build() {
            return new ListBean(itemType, imageUrl, text, value, id, fragment, onCheckedChangeListener);
        }
    }


}
