package com.jy.jiandao.detail.page;

import android.graphics.Color;
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
import com.mr.k.libmvp.widget.ToggleStateView;

import java.util.ArrayList;
import java.util.List;

public class DetailPageListAdapter2 extends BaseRecyclerAdapter2<RelativeNewsData.News,Comment> {



    private static final int TYPE_NEWS = 0X100;
    private static final int TYPE_COMMNETS = 0X101;



    private OnDetailItemOnClickListener itemOnClickListener;



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

            //itemView.setBackgroundColor(Color.GREEN);

        }
    }


    public void setOnItemClickListener(OnDetailItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    protected void onData1ItemClicke(RelativeNewsData.News data1, int postion) {
        super.onData1ItemClicke(data1, postion);

        if(itemOnClickListener != null){
            itemOnClickListener.onNewsClick((ArrayList<RelativeNewsData.News>) getData1List(),postion);
        }

    }





    private class CommentHolder extends BaseAdapter{

        private ImageView headPic;
        private TextView nickName;
        private TextView time;

        private ToggleStateView like;

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

            more.setOnClickListener((View v)-> {
                if(itemOnClickListener != null){
                    itemOnClickListener.onLoadMoreClick(getData2ByPosition(getAdapterPosition()),getAdapterPosition());
                }

            });



            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemOnClickListener != null){
                        itemOnClickListener.onLickClick(getData2ByPosition(getAdapterPosition()),getAdapterPosition());
                    }
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


            // 设置回复列表数据
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
            title = findViewById(R.id.item_news_right_tv_title);

            pic = findViewById(R.id.item_news_right_iv_pic);

            label = findViewById(R.id.item_news_right_tv_label);

        }


        public void bindData1(RelativeNewsData.News data) {

            title.setText(data.getTheme());


            GlideApp.with(itemView).load(data.getImageUrl()).into(pic);

            label.setText(data.getColumnName());
        }


    }




    public interface OnDetailItemOnClickListener{

        //相关新闻被点击
        void onNewsClick(ArrayList<RelativeNewsData.News> news, int position);

        // 评论里面查看更多回复
        void onLoadMoreClick(Comment comment,int position);


        // 评论点赞
        void onLickClick(Comment comment,int position);

    }


}
