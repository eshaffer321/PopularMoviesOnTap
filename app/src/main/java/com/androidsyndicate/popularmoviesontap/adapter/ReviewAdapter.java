package com.androidsyndicate.popularmoviesontap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidsyndicate.popularmoviesontap.R;
import com.androidsyndicate.popularmoviesontap.model.Reviews;


import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private Context mContext;
    private List<Reviews.ReviewBean> reviewList;

    public ReviewAdapter(Context context, List<Reviews.ReviewBean> reviewList){
        this.reviewList = reviewList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item,parent,false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reviews.ReviewBean review = reviewList.get(position);
        String author = review.getAuthor();
        String content = review.getContent();
        holder.bind(author,content);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mAuthorTextView;
        TextView mReviewContextTextView;
        Context mContext;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.review_author);
            mReviewContextTextView = (TextView) itemView.findViewById(R.id.review_content);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            //might not be needed
        }

        void bind(String author, String content){
            mAuthorTextView.setText(author);
            mReviewContextTextView.setText(content);
        }
    }
}