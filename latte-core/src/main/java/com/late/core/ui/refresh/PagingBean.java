package com.late.core.ui.refresh;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class PagingBean {
    //当前显示的为第几页
    private int mPageIndex = 0;
    //总数据条数
    private int mTotal = 0;
    //一页显示几条
    private int mPageSize = 0;
    //当前已显示几条
    private int mCurrentCount = 0;
    //加载延迟
    private int mDelayed = 0;

    public int getmPageIndex() {
        return mPageIndex;
    }

    public PagingBean setmPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this;
    }

    public int getmTotal() {
        return mTotal;
    }

    public PagingBean setmTotal(int mTotal) {
        this.mTotal = mTotal;
        return this;
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public PagingBean setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getmCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setmCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }

    public int getmDelayed() {
        return mDelayed;
    }

    public PagingBean setmDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this;
    }

    PagingBean addIndex(){
        mPageIndex++;
        return this;
    }
}
