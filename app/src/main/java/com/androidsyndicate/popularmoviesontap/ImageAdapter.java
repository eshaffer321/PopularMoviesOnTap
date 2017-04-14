package com.androidsyndicate.popularmoviesontap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<String> mMovieUrls;
    private Context mContext;
    private static String MY_TAG = "ImageAdapter";

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener{
        void onMoviePosterItemClick(int moviePosterIndex);
    }

    public ImageAdapter(ArrayList<String> list, Context context, ListItemClickListener listener) {
        mMovieUrls = list;
        mContext = context;
        mOnClickListener = listener;
    }//constructor

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mContext);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = mMovieUrls.get(position);
        holder.bind(url);
    }

    @Override
    public int getItemCount() {
        return mMovieUrls.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mImageItemView;
        public Context mContext;

        public ViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            mImageItemView = (ImageView) itemView.findViewById(R.id.iv_list_item);
            itemView.setOnClickListener(this);
        }

        void bind(String url) {
            Picasso.with(mContext).load(url).placeholder(R.drawable.loading)
                    .error(R.drawable.error).fit()
                    .into(mImageItemView);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onMoviePosterItemClick(position);

        }
    }//end nested class ViewHolder
}//end ImageAdapter class
