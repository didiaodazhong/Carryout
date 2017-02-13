package com.peixing.carryout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.model.net.bean.Category;
import com.peixing.carryout.model.net.bean.Head;
import com.peixing.carryout.model.net.bean.HomeInfo;
import com.peixing.carryout.model.net.bean.HomeItem;
import com.peixing.carryout.model.net.bean.Promotion;
import com.peixing.carryout.model.net.bean.Seller;
import com.peixing.carryout.ui.activity.SellerDetailActivity;
import com.peixing.carryout.utils.ShoppingCartManager;
import com.squareup.picasso.Picasso;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    //    private List<String> lists;
    private HomeInfo data;
    private final int TYPE_HEAD = 0;
    private final int TYPE_SELLER = 1;
    private final int TYPE_RECOMMEND = 2;


    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(HomeInfo datas) {
        this.data = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEAD:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_title, viewGroup, false);
                holder = new HeadHolder(view);
                break;
            case TYPE_SELLER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_seller, viewGroup, false);
                holder = new SellerHolder(view);
                break;
            case TYPE_RECOMMEND:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_division, viewGroup, false);
                holder = new RecommentHoler(view);
                break;
        }
//        view = LayoutInflater.from(mContext).inflate(R.layout.item_home_layout, viewGroup, false);

//        holder = new RecyclerView.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemViewHolder, int position) {

        //Here you can fill your row view
        int viewType = itemViewHolder.getItemViewType();
        switch (viewType) {
            case TYPE_HEAD:
                ((HeadHolder) itemViewHolder).setData(data.head);
                break;
            case TYPE_SELLER:
                HomeItem item = data.body.get(position - 1);
                ((SellerHolder) itemViewHolder).setData(item.seller);
                break;
            case TYPE_RECOMMEND:
                ((RecommentHoler) itemViewHolder).setData(data.body.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.body.size() + 1;
//        return lists == null ? 0 : lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            type = TYPE_HEAD;
        } else {
            HomeItem item = data.body.get(position - 1);
            type = item.type == 0 ? TYPE_SELLER : TYPE_RECOMMEND;
        }
        return type;
    }


    public class HeadHolder extends RecyclerView.ViewHolder {
        private SliderLayout slider;
        private LinearLayout catetoryContainer;
        private Head heads;


        public HeadHolder(View itemView) {
            super(itemView);

            slider = (SliderLayout) itemView.findViewById(R.id.slider);
            catetoryContainer = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }

        public void setData(Head heads) {
            this.heads = heads;
            slider.removeAllSliders();
            if (data != null && heads.promotionList.size() > 0) {
                for (Promotion item : heads.promotionList
                        ) {
                    TextSliderView textSliderView = new TextSliderView(MyApplication.getmContext());
                    textSliderView.image(item.pic);
                    textSliderView.description(item.info);
                    slider.addSlider(textSliderView);
                }
            }
            if (heads != null && heads.categorieList.size() > 0) {
                catetoryContainer.removeAllViews();
                View item = null;
                for (int i = 0, length = heads.categorieList.size(); i < length; i++) {
                    Category category = heads.categorieList.get(i);
                    if (i % 2 == 0) {
                        item = View.inflate(MyApplication.getmContext(), R.layout.item_home_head_category, null);
                        Picasso.with(MyApplication.getmContext())
                                .with(MyApplication.getmContext())
                                .load(category.pic)
                                .placeholder(R.drawable.item_logo)
                                .into((ImageView) item.findViewById(R.id.top_iv));
                        ((TextView) item.findViewById(R.id.top_tv)).setText(category.name);
                        catetoryContainer.addView(item);
                    }
                    if (i % 2 != 0) {
                        Picasso.with(MyApplication.getmContext())
                                .load(category.pic)
                                .placeholder(R.drawable.item_logo)
                                .into((ImageView) item.findViewById(R.id.bottom_iv));
                        ((TextView) item.findViewById(R.id.bottom_tv)).setText(category.name);
                    }
                }
            }
        }
    }


    public class RecommentHoler extends RecyclerView.ViewHolder {
        private HomeItem data;

        public RecommentHoler(View itemView) {
            super(itemView);
        }

        public void setData(HomeItem data) {
            this.data = data;
        }
    }


    public class SellerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCount;
        private TextView tvTitle;
        private RatingBar ratingBar;
        private Seller data;

        public SellerHolder(View itemView) {
            super(itemView);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        public void setData(Seller data) {
            this.data = data;
            tvTitle.setText(data.name);
            //设置已经购买数量，根据商家表示进行区分
            if (data.id == ShoppingCartManager.getInstance().sellerId) {
                Integer num = ShoppingCartManager.getInstance().getTotalNum();
                if (num > 0) {
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(num.toString());
                } else {
                    tvCount.setVisibility(View.GONE);
                }
            }else {
                tvCount.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getmContext(), SellerDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("sid", data.id);
            intent.putExtra("name", data.name);
            if (ShoppingCartManager.getInstance().sellerId != data.id) {
                //进入商家是更新购物车商家标识
                ShoppingCartManager.getInstance().sellerId = data.id;
                ShoppingCartManager.getInstance().name = data.name;
                ShoppingCartManager.getInstance().url = data.pic;
                ShoppingCartManager.getInstance().sendPrice = data.sendPrice;
                ShoppingCartManager.getInstance().clear();
            }
            MyApplication.getmContext().startActivity(intent);
        }
    }
}
