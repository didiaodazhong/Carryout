package com.peixing.carryout.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.model.net.bean.GoodsInfo;
import com.peixing.carryout.utils.NumberFormatUtils;
import com.peixing.carryout.utils.ShoppingCartManager;
import com.squareup.picasso.Picasso;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class SettleCenterAdapter extends RecyclerView.Adapter<SettleCenterAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_settle_center_goods, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder itemViewHolder, int position) {

        //Here you can fill your row view
        itemViewHolder.setData(ShoppingCartManager.getInstance().goodsInfos.get(position));

    }

    @Override
    public int getItemCount() {

        return ShoppingCartManager.getInstance().goodsInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private GoodsInfo data;
        private TextView tvName;
        private TextView tvCount;
        private TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

        }

        public void setData(GoodsInfo data) {
            this.data = data;
            tvName.setText(data.name);
            tvPrice.setText(NumberFormatUtils.formatDigits(data.newPrice));
            tvCount.setText(data.count + "");
        }
    }
}
