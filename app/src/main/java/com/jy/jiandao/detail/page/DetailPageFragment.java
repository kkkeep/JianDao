package com.jy.jiandao.detail.page;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.AppConstant;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.BaseNews;
import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.data.entity.Relay;
import com.jy.jiandao.data.entity.ReplayListData;
import com.jy.jiandao.detail.IDetalContract;
import com.mr.k.libmvp.Utils.Logger;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseMvpFragment;
import com.mr.k.libmvp.widget.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DetailPageFragment  extends BaseMvpFragment<IDetalContract.IDetailPagePresenter> implements IDetalContract.IDetailPageView {

    private static final String TAG = "DetailPageFragment";

    private WebView mWebView;

    private RecyclerView mRecyclerView;

    private SmartRefreshLayout mSmartRefreshLayout;


    private BaseNews mNews;


    private RelativeNewsData mRelativeNewsData;

    private CommentListData mCommentListData;

    private DetailPageListAdapter mDetailPageListAdapter;

    private int mResponseCount;

    private boolean isWebViewLoadSuccess; // web view 加载完成


    @Override
    public void setArguments(@androidx.annotation.Nullable Bundle args) {
        super.setArguments(args);


        if(args != null){

            mNews = args.getParcelable(AppConstant.BundleKey.DETAIL_NEWS);

        }



    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_page;
    }





    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        mWebView = findViewById(R.id.detailWebView);

        mSmartRefreshLayout = findViewById(R.id.detailSrl);

        mRecyclerView = findViewById(R.id.detailList);


        mSmartRefreshLayout.setEnableRefresh(false);// 不让下拉刷新


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter((mDetailPageListAdapter = new DetailPageListAdapter()));


       // mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);

        initWebView(mWebView);

    }

    @Override
    protected void loadData() {

        showFullLoadingView(getRootViewId());


        loadWebContent(mNews.getLink());

        loadRelativeNewList();

        //loadCommentList();


    }


    /**
     * 加载相关新闻
     */
    private void loadRelativeNewList(){
        mResponseCount++;
    }

    private void loadWebContent(String url){

        mResponseCount++;

        mWebView.loadUrl(url);

    }

    /**
     * 加载评论列表（一级列表）
     */
    private void loadCommentList(){
        mResponseCount++;
    }

    @Override
    public IDetalContract.IDetailPagePresenter createPresenter() {
        return null;
    }

    @Override
    public void onRelativeNewsListResult(RelativeNewsData data, String msg) {

        mResponseCount--;

        mRelativeNewsData = data;
        handResponseData();


    }

    @Override
    public void onCommentListResult(CommentListData data, String msg) {


        mResponseCount--;
        mCommentListData = data;
        handResponseData();



    }


    private void handResponseData(){

        if(mResponseCount == 0){ // 只有最后一个请求完成后，才能关闭loading 页，并显示数据


            List<RelativeNewsData.News> news = null;

            if(mRelativeNewsData != null && !SystemFacade.isListEmpty(mRelativeNewsData.getList())){ // 如果有相关新闻
                news = mRelativeNewsData.getList();
            }


            List<Comment> comments = null;


            if(mCommentListData != null &&! SystemFacade.isListEmpty(mCommentListData.getCommentList())){ // 如果有评论数据

                comments = mCommentListData.getCommentList();
            }


            if(!isWebViewLoadSuccess && news == null && comments == null){ // 只有三种数据都失败才才显示错误页面
                showErrorLoadingView(new LoadingView.OnRetryListener() {
                    @Override
                    public void retry() {

                        loadData();

                    }
                });
            }else{
                closeLoadingView();
                mDetailPageListAdapter.setData(comments,news);

            }

        }

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


    private void initWebView(WebView webView){


        WebSettings webSettings =  webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(false);

        webSettings.setUseWideViewPort(true);

        webSettings.setDisplayZoomControls(false);

        webSettings.setBuiltInZoomControls(false);

        webSettings.setSupportMultipleWindows(true);

        webSettings.setAppCacheEnabled(true);

        webSettings.setDomStorageEnabled(true);

        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);

        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webSettings.setLoadWithOverviewMode(true);

        webSettings.setGeolocationEnabled(true);

        webSettings.setMediaPlaybackRequiresUserGesture(false);




        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);


                Logger.d("%s newProgress = %s",TAG,newProgress);
                if(newProgress == 100 && !isWebViewLoadSuccess){
                    Logger.d("%s newProgress ------ = %s",TAG,newProgress);
                    isWebViewLoadSuccess = true;
                    mResponseCount--;
                    handResponseData();
                }
            }


        }
        );

        webView.setWebViewClient(new WebViewClient(){


            /**
             * 一. 什么时候调用:
             *
             *     当新的 url 即将被加载的时候，也就是用户点击了 Webview 内容里面的一个超链接的时候会触发该方法的调用；
             *
             * 二. 为什么要实现：
             *
             *     我们需要新的链接 url 就加载在当前 WebView 里面，或者我们需要自己的应用程序去处理响应该链接；
             *     大概意思就是：
             *
             *     提供给当前应用一个机会去单独处理 WebView 即将加载的一个新链接。如果 WebViewClient 没有设置，也就是没有调用 WebView 的 setWebViewClient 方法，
             * 那默认就会让用户去选择一个浏览器应用，比如系统浏览器去加载这个新的链接了(并不是所有的手机或者版本都会调用系统浏览器，有的手机上不调用)。那如果 WebViewClient 是设置了的（用webview 自己处理），
             * 返回 True 代表当前应用已经处理了这个新链接了，不需要你 WebView 再去加载这个链接了，当然了，返回 False 的话 WebView 就会横插一脚，去加载这个新的链接。
             *
             *
             * 注意：某些链接点击时，即使返回 true，也能加载，但是有的链接 返回true 点击了就没反应，所以，为了让webview 里面发生点击能在当前webview 加载，那么return false. 默认也是return false
             *
             * @param view
             * @param url
             * @return
             */

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mResponseCount--;
                isWebViewLoadSuccess = false;

            }
        });


    }

}