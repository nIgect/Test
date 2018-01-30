package com.tarasevich.test.MyImageViewAdapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.RequestManager;
import com.tarasevich.test.Constants.Constants;
import com.tarasevich.test.CustomImageRetrofitLoader.ImageResponse;
import com.tarasevich.test.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyImageViewAdapter extends RecyclerView.Adapter<MyImageViewAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder  {


        @BindView(R.id.dateImage)
        TextView dateImage;
        @BindView(R.id.imageViewForRecyclerView)
        ImageView imageViewForRecyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    private void bind(ImageResponse.Image image, RequestManager requestManager){
            dateImage.setText(image.date);
            requestManager.load(image.url).into(imageViewForRecyclerView);
    }

    }

    private ArrayList<ImageResponse.Image> mImageResponses = new ArrayList<>();
    private RequestManager mRequestManager;


    public MyImageViewAdapter(RequestManager requestManager) {
        mRequestManager = requestManager;

    }


    @Override
    public int getItemCount() {
        return mImageResponses.size();
    }


    ImageResponse.Image getItem(int position) {
        return mImageResponses.get(position) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     holder.bind(mImageResponses.get(position),mRequestManager);

    }
    public void updateImages(ImageResponse response){
        if(response!=null){
            mImageResponses.clear();
            mImageResponses.addAll(response);
            notifyDataSetChanged();
        }
    }

}
