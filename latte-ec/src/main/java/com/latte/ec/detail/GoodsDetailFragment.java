package com.latte.ec.detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.joanzapata.iconify.widget.IconTextView;
import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.ui.banner.HolderCreator;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.R;
import com.latte.ui.animation.BezierAnimation;
import com.latte.ui.animation.BezierUtil;
import com.latte.ui.widget.CircleTextView;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Administrator on 2018\11\16 0016.
 */

public class GoodsDetailFragment extends LatteFragment implements
        AppBarLayout.OnOffsetChangedListener, BezierUtil.AnimationListener {

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;

    private ConvenientBanner<String> mBanner = null;
    private TabLayout mTabLayout = null;
    private ViewPager mViewPager = null;

    private CircleTextView mCircleTextView;
    private RelativeLayout mBottonLayout;
    private IconTextView mIconShopCart;


    private int mGoodsId = -1;


    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }

    }

    public static GoodsDetailFragment create(int goodsId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailFragment fragment = new GoodsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mBanner = $(R.id.detail_banner);
        mTabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.view_pager);

        mCircleTextView = $(R.id.tv_shopping_cart_amount);
        mBottonLayout = $(R.id.rl_add_shop_cart);
        mIconShopCart = $(R.id.icon_shop_cart);
        $(R.id.rl_add_shop_cart).setOnClickListener(view -> onConClickAddShopCart());
        myOnClick();

        final CollapsingToolbarLayout collapsingToolbarLayout = $(R.id.collapsing_toolbar_detail);
        final AppBarLayout mAppBar = $(R.id.app_bar_detail);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBar.addOnOffsetChangedListener(this::onOffsetChanged);

        initData();
        initTabLayout();
    }

    private void onConClickAddShopCart() {
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);
        BezierAnimation.addCart(this, mBottonLayout, mIconShopCart, animImg, this);

    }

    private void setShopCartCount(JSONObject data) {
        mGoodsThumbUrl = data.getString("thumb");
        if (mShopCount == 0) {
            mCircleTextView.setVisibility(View.GONE);
        }
    }


    private void initData() {
        LatteLogger.d("detail", "mGoodsId:" + mGoodsId);
        RestClient.Builder()
                .url("goods_detail.php")
                .params("goods_id", mGoodsId)
                .loader(getContext())
                .success(response -> {
                    if (!StringUtils.isEmpty(response) || !StringUtils.isEmpty(response)) {
                        LatteLogger.d("detail", response);
                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                        initPager(data);
                        setShopCartCount(data);
                    }
                })
                .build()
                .get();
    }


    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(array.getString(i));
        }
        mBanner.setPages(new HolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    private void initGoodsInfo(JSONObject data) {
        final String goodsData = data.toJSONString();
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info, GoodsInfoFragment.create(goodsData));
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        final Context context = getContext();
        if (context != null) {
            mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.app_main));
        }
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPager(JSONObject data) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(adapter);
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    private void myOnClick() {
        $(R.id.icon_goods_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onAnimationEnd() {
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);
//        RestClient.Builder()
//                .url("add_shop_cart_count.php")
//                .loader(getContext())
//                .success(response -> {
//                    LatteLogger.json("ADD", response);
//                    final boolean isAdded = JSON.parseObject(response).getBoolean("data");
//                    if (isAdded) {
//                        mShopCount++;
//                        mCircleTextView.setVisibility(View.VISIBLE);
//                        mCircleTextView.setText(String.valueOf(mShopCount));
//                    }
//                })
//                .params("count", mShopCount)
//                .build()
//                .post();
        mShopCount++;
        mCircleTextView.setVisibility(View.VISIBLE);
        mCircleTextView.setText(String.valueOf(mShopCount));
        mCircleTextView.setCircleBackgroud(getResources().getColor(R.color.app_main));
    }
}