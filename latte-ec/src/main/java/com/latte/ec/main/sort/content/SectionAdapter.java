package com.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.latte.ec.R;

import java.util.List;

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int id = item.t.getGoodsId();

        final SectionContentItemEntity entity = item.t;
        helper.setText(R.id.tv, name);
        final AppCompatImageView goodImageView = helper.getView(R.id.iv);
        Glide.with(mContext)
                .load(thumb)
                .into(goodImageView);
    }
}
