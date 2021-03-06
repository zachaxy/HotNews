package com.zx.hotnews.engine.movie;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zx.hotnews.R;
import com.zx.hotnews.base.BaseFragment;
import com.zx.hotnews.bean.movie.SubjectsBean;
import com.zx.hotnews.engine.picture.PictureFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxin on 2016/11/26.
 * <p>
 * Description :
 */
public class MovieFragment extends BaseFragment implements MovieContract.View {
    RecyclerView mRecyclerView;
    ArrayList<SubjectsBean> list = new ArrayList();
    MovieAdapter mAdapter;

    //UI部分,考虑要不要设置到Base中;
    // 加载中,一进来是有的,数据加载完设置为gone;
    private LinearLayout mLlProgressBar;

    // 加载失败
    private LinearLayout mRefresh;

    // 内容布局
    protected RelativeLayout mContainer;

    // 动画
    private AnimationDrawable mAnimationDrawable;

    MoviePresenter mPresenter;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new MovieAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mAdapter);

        mLlProgressBar = (LinearLayout) view.findViewById(R.id.ll_progress_bar);
        ImageView img = (ImageView) view.findViewById(R.id.img_progress);
        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        mRefresh = (LinearLayout) view.findViewById(R.id.ll_error_refresh);
        // 点击加载失败布局
        mRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPresenter.getMovie();
            }
        });

        //初始化数据
        mPresenter = new MoviePresenter(this);//让Presenter持有了view;
        mPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    public static MovieFragment getInstance() {
        MovieFragment movieFragment = new MovieFragment();
        return movieFragment;
    }


//############################################################


    @Override
    public void load(List<SubjectsBean> datas) {
        list.addAll(datas);
    }

    @Override
    public void showError() {
        //隐藏加载中...
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        //显示错误提示;
        if (mRefresh.getVisibility() != View.VISIBLE) {
            mRefresh.setVisibility(View.VISIBLE);
        }

    }

    //用于数据加载完之后,通知RecyclerView来改变数据;在这里修改一些UI吧;
    @Override
    public void showNormal() {
        //隐藏加载中...
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        //隐藏显示错误
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public PictureFragment getFragment() {
        return null;
    }
}
