package com.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.late.core.ui.recycler.ItemType;
import com.late.core.ui.recycler.MultipleFields;
import com.late.core.ui.recycler.MultipleItemEntity;
import com.late.core.ui.recycler.MultipleRecyclerAdapter;
import com.late.core.ui.recycler.MultipleViewHolder;
import com.latte.ec.R;
import com.latte.ec.main.sort.SortFragment;

import java.util.List;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class SortRecyclerAdapter extends MultipleRecyclerAdapter{

    private final SortFragment FRAGMENT;
    private int mPrePosition = 0;

    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortFragment fragment) {
        super(data);
        this.FRAGMENT = fragment;
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                //整个横条
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentPosition = holder.getAdapterPosition();
                        if (mPrePosition != currentPosition){
                            //还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);

                        }
                    }
                });
                Log.d("convert", "convert");
                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));

                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor( Color.WHITE);
                }

                holder.setText(R.id.tv_vertical_item_name, text);


                break;
        }
    }
}
