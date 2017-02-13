package com.peixing.carryout.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.model.net.bean.GoodsInfo;
import com.peixing.carryout.utils.NumberFormatUtils;
import com.peixing.carryout.utils.ShoppingCartManager;
import com.squareup.picasso.Picasso;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartHolder> {

    @Override
    public CartHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(MyApplication.getmContext()).inflate(R.layout.item_cart, null);

        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(CartHolder itemViewHolder, int position) {

        //Here you can fill your row view
        itemViewHolder.setData(ShoppingCartManager.getInstance().goodsInfos.get(position));
    }

    @Override
    public int getItemCount() {

        return ShoppingCartManager.getInstance().goodsInfos.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private ImageView itemIv;
        private TextView itemTvName;
        private TextView itemTvPrice;
        private TextView itemTvNum;

        private GoodsInfo data;

        public CartHolder(View itemView) {
            super(itemView);
            itemIv = (ImageView) itemView.findViewById(R.id.item_iv);
            itemTvName = (TextView) itemView.findViewById(R.id.item_tv_name);
            itemTvPrice = (TextView) itemView.findViewById(R.id.item_tv_price);
            itemTvNum = (TextView) itemView.findViewById(R.id.item_tv_num);
        }

        public void setData(GoodsInfo data) {
            this.data = data;
            Picasso.with(MyApplication.getmContext()).load(data.icon).into(itemIv);
            itemTvName.setText(data.name);
            itemTvPrice.setText(NumberFormatUtils.formatDigits(data.newPrice));
            itemTvNum.setText(data.count + "");
        }
    }
}
