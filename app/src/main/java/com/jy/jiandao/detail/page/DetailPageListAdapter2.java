package com.jy.jiandao.detail.page;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.CommentListData;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.detail.widget.CommentsView;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseRecyclerAdapter2;

import java.util.List;

public class DetailPageListAdapter2 extends BaseRecyclerAdapter2<RelativeNewsData.News,Comment> {



    private static final int TYPE_NEWS = 0X100;
    private static final int TYPE_COMMNETS = 0X101;

    @Override
    public int getItemLayoutId(int viewType) {
        if(viewType == TYPE_NEWS){
            return R.layout.item_recommend_news_right;
        }
        return R.layout.item_detial_comment;
    }

    @Override
    public BaseAdapterHolder2 createHolder(View itemView, int viewType) {
        if(viewType == TYPE_NEWS){
            return new NewsHolder(itemView);
        }
        return new CommentHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if(!SystemFacade.isListEmpty(getData1List())){
            if(position < getData1List().size()){
                return TYPE_NEWS;
            }
        }

        return TYPE_COMMNETS;
    }



    private class BaseAdapter extends  BaseRecyclerAdapter2.BaseAdapterHolder2<RelativeNewsData.News,Comment>{

        public BaseAdapter(@NonNull View itemView) {
            super(itemView);
        }
    }


    @Override
    protected void onData1ItemClicke(RelativeNewsData.News data1, int postion) {
        super.onData1ItemClicke(data1, postion);

        Log.d("Test",data1.getTheme() + " _ " + postion);
    }

    @Override
    protected void onData2ItemClicke(Comment data2, int postion) {
        super.onData2ItemClicke(data2, postion);
        Log.d("Test",data2.getContent() + " _ " + postion);
    }

    private class CommentHolder extends BaseAdapter{

        private ImageView headPic;
        private TextView nickName;
        private TextView time;

        private CheckBox like;

        private TextView content;

        private CommentsView replayListView;

        private TextView more;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);


            headPic  = findViewById(R.id.detail_comment_item_user_head_pic);

            nickName  = itemView.findViewById(R.id.detail_comment_item_tv_username);

            time = itemView.findViewById(R.id.detail_comment_item_tv_time);

            like = itemView.findViewById(R.id.detail_comment_item_cb_lick);

            content = itemView.findViewById(R.id.detail_comment_item_tv_content);

            replayListView = itemView.findViewById(R.id.detail_comment_item_reply);

            more = itemView.findViewById(R.id.detail_comment_item_show_more);

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void bindData2(Comment data) {

            GlideApp.with(itemView).load(data.getHeadUrl()).into(headPic);


            nickName.setText(data.getUserName());


            time.setText(data.getTimeDescribe());

            like.setText(String.valueOf(data.getPraiseCountDescribe()));

            like.setChecked(data.isPraise()); //

            content.setText(data.getContent());

            replayListView.setList(data.getReplyList());

            replayListView.notifyDataSetChanged();

            if(data.getReplyMore() == 1){
                more.setVisibility(View.VISIBLE);
            }else{
                more.setVisibility(View.GONE);
            }

        }



    }


    private class NewsHolder extends BaseAdapter {

        private TextView title;
        private ImageView pic;

        private  TextView label;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_news_right_tv_title);

            pic = itemView.findViewById(R.id.item_news_right_iv_pic);

            label = itemView.findViewById(R.id.item_news_right_tv_label);

        }


        public void bindData1(RelativeNewsData.News data) {

            title.setText(data.getTheme());


            GlideApp.with(itemView).load(data.getImageUrl()).into(pic);

            label.setText(data.getColumnName());
        }


    }


}
