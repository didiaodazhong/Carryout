package com.peixing.carryout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class LocateAdapter extends RecyclerView.Adapter<LocateAdapter.ViewHolder> {
    private List<PoiItem> poiItems;
    private Context context;

    public LocateAdapter(List<PoiItem> poiItems, Context context) {
        this.poiItems = poiItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_select_receipt_address, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder itemViewHolder, int position) {

        //Here you can fill your row view
        PoiItem poiItem = poiItems.get(position);
//        itemViewHolder.tvTitle.setText(poiItem.getTitle());
//        itemViewHolder.tvSnippet.setText(poiItem.getSnippet());
        itemViewHolder.setdata(poiItem);
    }

    @Override
    public int getItemCount() {
        return poiItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tvTitle;
        private TextView tvSnippet;
        private PoiItem data;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSnippet = (TextView) itemView.findViewById(R.id.tv_snippet);

        }

        public void setdata(final PoiItem data) {
            this.data = data;
            tvTitle.setText(data.getTitle());
            tvSnippet.setText(data.getSnippet());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title",data.getTitle());
                    intent.putExtra("snippet",data.getSnippet());

                    LatLonPoint point = data.getLatLonPoint();

                    intent.putExtra("latitude",point.getLatitude());
                    intent.putExtra("longitude", point.getLongitude());

                }
            });
        }
    }
}
