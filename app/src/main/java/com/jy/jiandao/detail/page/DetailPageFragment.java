package com.jy.jiandao.detail.page;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.data.entity.Relay;
import com.jy.jiandao.data.entity.ReplayListData;
import com.jy.jiandao.detail.IDetalContract;
import com.mr.k.libmvp.base.BaseMvpFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DetailPageFragment  extends BaseMvpFragment<IDetalContract.IDetailPagePresenter> implements IDetalContract.IDetailPageView {


    private TextView textView;


    private BaseNews baseNews;


    @Override
    public void setArguments(@androidx.annotation.Nullable Bundle args) {
        super.setArguments(args);


        if(args != null){

            baseNews = (BaseNews) args.getSerializable("news");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_apge;
    }





    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        textView = findViewById(R.id.test_news_id);

        textView.setText(baseNews.getTheme());
    }

    @Override
    public IDetalContract.IDetailPagePresenter createPresenter() {
        return null;
    }

    @Override
    public void onRelativeNewsListResult(RelativeNewsData data, String msg) {

    }

    @Override
    public void onCommentListResult(CommentListData data, String msg) {

    }

    @Override
    public void onCommentRelayListResult(ReplayListData data, String msg) {

    }

    @Override
    public void onDoCommentResult(Comment data, String msg) {

    }

    @Override
    public void onDoReplayResult(Relay data, String msg) {

    }

}
