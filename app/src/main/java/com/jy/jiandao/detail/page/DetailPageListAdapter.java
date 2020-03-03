package com.jy.jiandao.detail.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.jiandao.GlideApp;
import com.jy.jiandao.R;
import com.jy.jiandao.data.entity.Comment;
import com.jy.jiandao.data.entity.RelativeNewsData;
import com.jy.jiandao.detail.widget.CommentsView;
import com.mr.k.libmvp.Utils.SystemFacade;
import com.mr.k.libmvp.base.BaseAdapterHolder;
import com.mr.k.libmvp.base.BaseRecyclerAdapter;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.http.HEAD;

public class DetailPageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_NEWS = 0X100;
    private static final int TYPE_COMMNETS = 0X101;


    private List<RelativeNewsData.News> mNews;
    private List<Comment> mComments;


    public void setData(List<Comment> comments, List<RelativeNewsData.News> news) {
        mNews = news;
        this.mComments = comments;

        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_NEWS) {
            return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_news_right, parent, false));
        }
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detial_comment, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);

        if (type == TYPE_NEWS) {

            ((NewsHolder) holder).bindData(mNews.get(position));
        } else {

            ((CommentHolder) holder).bindData(mComments.get(getRealCommentPostion(position)));
        }
    }


    private int getRealCommentPostion(int position) {

        if (SystemFacade.isListEmpty(mNews)) {
            return position;
        } else {

            return position - mNews.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!SystemFacade.isListEmpty(mNews)) {
            if (position < mNews.size()) {
                return TYPE_NEWS;
            }
        }

        return TYPE_COMMNETS;
    }

    @Override
    public int getItemCount() {
        int count = mNews == null ? 0 : mNews.size();


        if (!SystemFacade.isListEmpty(mComments)) {
            count = count + mComments.size();
        }

        return count;
    }


    private class NewsHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView pic;

        private TextView label;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_news_right_tv_title);

            pic = itemView.findViewById(R.id.item_news_right_iv_pic);

            label = itemView.findViewById(R.id.item_news_right_tv_label);

        }


        public void bindData(RelativeNewsData.News data) {

            title.setText(data.getTheme());


            GlideApp.with(itemView).load(data.getImageUrl()).into(pic);

            label.setText(data.getColumnName());
        }


    }


    private class CommentHolder extends RecyclerView.ViewHolder {

        private ImageView headPic;
        private TextView nickName;
        private TextView time;

        private CheckBox like;

        private TextView content;

        private CommentsView replayListView;

        private TextView more;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);


            headPic = itemView.findViewById(R.id.detail_comment_item_user_head_pic);

            nickName = itemView.findViewById(R.id.detail_comment_item_tv_username);

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

        public void bindData(Comment data) {


            GlideApp.with(itemView).load(data.getHeadUrl()).into(headPic);


            nickName.setText(data.getUserName());


            time.setText(data.getTimeDescribe());

            like.setText(String.valueOf(data.getPraiseCountDescribe()));

            like.setChecked(data.isPraise()); //

            content.setText(data.getContent());

            replayListView.setList(data.getReplyList());

            replayListView.notifyDataSetChanged();

            if (data.getReplyMore() == 1) {
                more.setVisibility(View.VISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }


        }


    }


}
