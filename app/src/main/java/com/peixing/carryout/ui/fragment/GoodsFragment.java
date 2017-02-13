package com.peixing.carryout.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.dagger.component.fragment.DaggerGoodsFragmentComponent;
import com.peixing.carryout.dagger.module.fragment.GoodsFragmentModule;
import com.peixing.carryout.model.net.bean.GoodsInfo;
import com.peixing.carryout.model.net.bean.GoodsTypeInfo;
import com.peixing.carryout.presenter.fragment.GoodsFragmentPresenter;
import com.peixing.carryout.ui.activity.CartActivity;
import com.peixing.carryout.utils.AnimationUtils;
import com.peixing.carryout.utils.NumberFormatUtils;
import com.peixing.carryout.utils.ShoppingCartManager;
import com.peixing.carryout.utils.UiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnClickListener {
    private static final String TAG = "GoodsFragment";
    //    private ArrayList<Data> datas = new ArrayList<>();
    private ArrayList<GoodsInfo> datas = new ArrayList<>();
    //    private ArrayList<Head> headDatas = new ArrayList<>();
    private ArrayList<GoodsTypeInfo> headDatas;
    private MyHeadAdapter headAdapter;
    private ListView lv;
    private StickyListHeadersListView shl;
    private RelativeLayout cart;
    private ImageView ivCart;
    private TextView fragmentGoodsTvCount;
    private MyGroupAdapter groupAdapter;

    @Inject
    GoodsFragmentPresenter presenter;
    private long sid;

    public GoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerGoodsFragmentComponent.builder().goodsFragmentModule(new GoodsFragmentModule(this)).build().in(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.lv);
        shl = (StickyListHeadersListView) view.findViewById(R.id.shl);
        cart = (RelativeLayout) view.findViewById(R.id.cart);
        ivCart = (ImageView) view.findViewById(R.id.iv_cart);
        fragmentGoodsTvCount = (TextView) view.findViewById(R.id.fragment_goods_tv_count);
        cart.setOnClickListener(this);
        sid = getArguments().getLong("seller_id");
//        Log.i(TAG, "onResume: sid" + sid);
        Integer totalNum = ShoppingCartManager.getInstance().getTotalNum();
        if (totalNum > 0) {
            fragmentGoodsTvCount.setVisibility(View.VISIBLE);
            fragmentGoodsTvCount.setText(totalNum.toString());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(sid);
    }

    private boolean isScroll = false;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        headAdapter.setSelectedPosition(position);
//        Head head = headDatas.get(position);
        GoodsTypeInfo head = headDatas.get(position);
        shl.setSelection(head.groupFirstIndex);
//        shl.setSelectedPosition(head.groupFirstIndex);
        isScroll = false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isScroll) {
//            Data data = datas.get(firstVisibleItem);
            GoodsInfo data = datas.get(firstVisibleItem);
//            System.out.println("data.headIndex:" + data.headIndex);
            // 当前正在置顶显示的头高亮处理
            headAdapter.setSelectedPosition(data.headIndex);
            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = lv.getFirstVisiblePosition();
            int lastVisiblePosition = lv.getLastVisiblePosition();
            if (data.headIndex <= firstVisiblePosition || data.headIndex >= lastVisiblePosition) {
                lv.setSelection(data.headIndex);// 可见处理
            }
        }
    }

    public void success(ArrayList<GoodsTypeInfo> goodsTypeInfos) {
        headDatas = goodsTypeInfos;

        for (int i = 0; i < headDatas.size(); i++) {
            GoodsTypeInfo head = headDatas.get(i);
            for (int j = 0; j < head.list.size(); j++) {
                GoodsInfo data = head.list.get(j);
                data.headId = head.id;
                data.headIndex = i;
                if (j == 0)
                    head.groupFirstIndex = datas.size();
                datas.add(data);
            }
        }
        headAdapter = new MyHeadAdapter(headDatas);
        lv.setAdapter(headAdapter);
        groupAdapter = new MyGroupAdapter(headDatas, datas);
        shl.setAdapter(groupAdapter);
        lv.setOnItemClickListener(this);
        shl.setOnScrollListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart:
                if (ShoppingCartManager.getInstance().getTotalNum() > 0) {
                    startActivity(new Intent(this.getContext(), CartActivity.class));
                }
                break;
            default:

                break;
        }
    }

    /**
     * 普通条目
     */
    class Data {
        String info;

        int headId;// 进行分组操作，同组数据该字段值相同

        int headIndex;  // 当前条目对应头数据所在集合的下标
    }

    /**
     * 头条目
     */
    class Head {
        String info;
        // 点击摸个头时，需要知道其分组容器中对应组元素中第一条的下标
        int groupFirstIndex;
    }

 /*   private void testData() {
        */

    /**
     * 头
     * 条目
     * 条目
     * 条目
     * 条目
     * 头
     * 条目
     * 条目
     * 条目
     * 条目
     *//*

        // 分组：0-9为一个分组
        if (headDatas.size() == 0) {
            for (int hi = 0; hi < 10; hi++) {
                Head head = new Head();
                head.info = "头：" + hi;
                headDatas.add(head);
            }

            // 普通条目
//        for(int di=0;di<100;di++){
//            Data data=new Data();
//            data.info="普通条目："+di;
//            datas.add(data);
//        }

            for (int hi = 0; hi < headDatas.size(); hi++) {

                Head head = headDatas.get(hi);
                // 普通条目
                for (int di = 0; di < 10; di++) {
                    Data data = new Data();
                    data.headId = hi;// 任意值
                    data.info = "普通条目：第" + hi + "组，条目数" + di;

                    data.headIndex = hi;

                    if (di == 0)
                        head.groupFirstIndex = datas.size();
                    datas.add(data);
                }
            }
        }
    }*/


    private class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private final ArrayList<GoodsTypeInfo> headDataSet;
        private final ArrayList<GoodsInfo> itemDataSet;

        public MyGroupAdapter(ArrayList<GoodsTypeInfo> headDataSet, ArrayList<GoodsInfo> itemDataSet) {
            this.headDataSet = headDataSet;
            this.itemDataSet = itemDataSet;
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {


           /* Data data = datas.get(position);
            // 头所在集合下标
            Head head = headDatas.get(data.headIndex);
            TextView tv = new TextView(MyApplication.getmContext());
            tv.setText(head.info);
            tv.setBackgroundColor(Color.GRAY);
            return tv;*/
            // 向下滚动：头数据加载的是每组的第一条
            // 向上滚动：头数据加载的是每组的最后一条

            TextView head = new TextView(parent.getContext());
            GoodsTypeInfo headData = headDataSet.get(itemDataSet.get(position).headIndex);
            head.setText(headData.name);
            head.setBackgroundColor(MyApplication.getmContext().getResources().getColor(R.color.colorItemBg));
            return head;
        }

        @Override
        public long getHeaderId(int position) {
            // 依据position获取普通条目
            // 普通条目中存放了headId
            return itemDataSet.get(position).headId;
        }

        @Override
        public int getCount() {
            return itemDataSet.size();
        }

        @Override
        public Object getItem(int position) {
            return itemDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           /* TextView tv = new TextView(MyApplication.getmContext());
            tv.setText(datas.get(position).name);
            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTextColor(Color.GRAY);
            return tv;*/
            GoodsInfo data = itemDataSet.get(position);
            ItemViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(MyApplication.getmContext(), R.layout.item_goods, null);
                holder = new ItemViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ItemViewHolder) convertView.getTag();
            }

            holder.setData(data);
            return convertView;
        }

        class ItemViewHolder implements View.OnClickListener {
            View itemView;
            private GoodsInfo data;

            private FrameLayout container;
            private TextView count;
            private ImageView ivIcon;
            private TextView tvName;
            private TextView tvZucheng;
            private TextView tvYueshaoshounum;
            private TextView tvNewprice;
            private TextView tvOldprice;
            private ImageButton ibMinus;
            private TextView tvCount;
            private ImageButton ibAdd;
            private Integer totalNum;

            public ItemViewHolder(View itemView) {
                this.itemView = itemView;
                ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvZucheng = (TextView) itemView.findViewById(R.id.tv_zucheng);
                tvYueshaoshounum = (TextView) itemView.findViewById(R.id.tv_yueshaoshounum);
                tvNewprice = (TextView) itemView.findViewById(R.id.tv_newprice);
                tvOldprice = (TextView) itemView.findViewById(R.id.tv_oldprice);
                ibMinus = (ImageButton) itemView.findViewById(R.id.ib_minus);
                tvCount = (TextView) itemView.findViewById(R.id.tv_money);
                ibAdd = (ImageButton) itemView.findViewById(R.id.ib_add);
                ibAdd.setOnClickListener(this);
                ibMinus.setOnClickListener(this);
            }

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ib_minus:
                        minusHander(v);
                        break;
                    case R.id.ib_add:
                        addHandler(v);
                        break;
                }
            }

         /*   */

            /**
             * 处理减少的操作
             *
             * @param v
             */
            private void minusHander(View v) {
                Integer num = ShoppingCartManager.getInstance().minusGoods(data);
                if (num == 0) {
                    // 动画处理
                    AnimationSet animation = AnimationUtils.getHideMinusAnimation();
                    ibMinus.startAnimation(animation);
                    tvCount.startAnimation(animation);
                    animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ibMinus.setVisibility(View.GONE);
                            tvCount.setVisibility(View.GONE);
                        }
                    });
                }


                tvCount.setText(num.toString());

                // 处理购物车气泡的显示
                totalNum = ShoppingCartManager.getInstance().getTotalNum();
                if (totalNum == 0) {
                    count.setVisibility(View.INVISIBLE);
                }
                count.setText(totalNum.toString());
            }


            /**
             * 添加动作处理
             *
             * @param
             */
            private void addHandler(View v) {
                Integer num = ShoppingCartManager.getInstance().addGoods(data);
                if (num == 1) {// 第一次动画执行

                    // 动画操作:－的ImageView和数量TextView
                    AnimationSet showMinusAnimation = AnimationUtils.getShowMinusAnimation();
                    tvCount.startAnimation(showMinusAnimation);
                    ibMinus.startAnimation(showMinusAnimation);

                    tvCount.setVisibility(View.VISIBLE);
                    ibMinus.setVisibility(View.VISIBLE);
                }

                tvCount.setText(num.toString());

                // 处理飞入到购物车的动画
                flyToShoppingCart(v);

                // 修改气泡提示
                if (count == null) {
                    count = (TextView) container.findViewById(R.id.fragment_goods_tv_count);
                }
                if (num > 0) {
                    count.setVisibility(View.VISIBLE);
                }

                totalNum = ShoppingCartManager.getInstance().getTotalNum();
                count.setText(totalNum.toString());
            }

            private void flyToShoppingCart(View v) {
                // 1、获取该控件所在的位置
                int[] location = new int[2];
                v.getLocationOnScreen(location);// 获取到控件所在整个屏幕的位置

                // 2、获取目标位置（购物车图片所在位置）
                int[] targetLocation = new int[2];
                // 将Activity加载控件容器获取到
                if (container == null) {
                    container = (FrameLayout) UiUtils.getContainder(v, R.id.frame_seller_detail);
                }
                container.findViewById(R.id.cart).getLocationOnScreen(targetLocation);

                // getLocationOnScreen获取控件所在屏幕中的位置，需要在y轴方向将状态栏的高度减掉
                location[1] -= UiUtils.STATUE_BAR_HEIGHT;
                targetLocation[1] -= UiUtils.STATUE_BAR_HEIGHT;

                // 创建一个控件，放到“+”按钮地方，执行动画
                final ImageView iv = getImageView(location, v);
                Animation animation = AnimationUtils.getAddAnimation(targetLocation, location);
                iv.startAnimation(animation);
                animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        container.removeView(iv);
                    }
                });
            }

            private ImageView getImageView(int[] location, View v) {
                ImageView iv = new ImageButton(MyApplication.getmContext());
                iv.setX(location[0]);
                iv.setY(location[1]);
                iv.setBackgroundResource(R.mipmap.food_button_add);


                // 将该控件放到何处（条目），存在问题，如果将控件放到条目中的话，在点击时执行动画，只能在条目中看到控制动画执行，超出条目的范围控件就不可见
//            ((ViewGroup)itemView).addView(iv,v.getWidth(),v.getHeight());
                container.addView(iv, v.getWidth(), v.getHeight());

                return iv;
            }

            public void setData(GoodsInfo data) {
                this.data = data;

                //图片
                Picasso.with(MyApplication.getmContext()).load(data.icon).into(ivIcon);
                tvName.setText(data.name);
                if (TextUtils.isEmpty(data.form)) {
                    tvZucheng.setVisibility(View.GONE);
                } else {
                    tvZucheng.setVisibility(View.VISIBLE);
                    tvZucheng.setText(data.form);
                }
                tvYueshaoshounum.setText("月销售" + data.monthSaleNum + "份");
                tvNewprice.setText(NumberFormatUtils.formatDigits(data.newPrice));
                if (data.oldPrice == 0) {
                    tvOldprice.setVisibility(View.GONE);
                } else {
                    tvOldprice.setVisibility(View.VISIBLE);
                    tvOldprice.setText(NumberFormatUtils.formatDigits(data.oldPrice));
                    //TextView出现中间的线
                    tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }

                // 判断购物重是否有当前条目的商品，如果有需要获取到商品的数据量
                Integer num = ShoppingCartManager.getInstance().getGoodsIdNum(data.id);
                if (num > 0) {
                    ibMinus.setVisibility(View.VISIBLE);
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(num.toString());
                } else {
                    ibMinus.setVisibility(View.INVISIBLE);
                    tvCount.setVisibility(View.INVISIBLE);
                }
            }
        }
    }


    class MyHeadAdapter extends BaseAdapter {
        //    private int selectedPosition;
        private ArrayList<GoodsTypeInfo> headDataSet;
        private int selectedHeadIndex;

        public MyHeadAdapter(ArrayList<GoodsTypeInfo> headDataSet) {
            this.headDataSet = headDataSet;
        }

        @Override
        public int getCount() {
            return headDataSet.size();
        }

        @Override
        public Object getItem(int position) {
            return headDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           /* TextView tv = new TextView(MyApplication.getmContext());
            tv.setText(headDatas.get(position).info);
            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setTextColor(Color.BLACK);

            if (position == selectedPosition) {
                tv.setBackgroundColor(Color.WHITE);
            } else {
                tv.setBackgroundColor(Color.GRAY);
            }
            return tv;*/
            GoodsTypeInfo data = headDataSet.get(position);
            HeadViewHolder holder;
            if (convertView == null) {
                convertView = new TextView(parent.getContext());
                holder = new HeadViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (HeadViewHolder) convertView.getTag();
            }
            holder.setData(data);

            if (position == selectedHeadIndex)
                holder.itemView.setBackgroundColor(Color.WHITE);

            return convertView;
        }

        public void setSelectedPosition(int selectedPosition) {
       /* if (this.selectedPosition == selectedPosition) {
            return;
        }
*/
            this.selectedHeadIndex = selectedPosition;
//            System.out.println("刷新");
            notifyDataSetChanged();
        }

        private class HeadViewHolder {

            private View itemView;
            private GoodsTypeInfo data;

            public HeadViewHolder(View itemView) {
                this.itemView = itemView;
            }

            public void setData(GoodsTypeInfo data) {
                this.data = data;
                ((TextView) itemView).setText(data.name);
                ((TextView) itemView).setBackgroundColor(MyApplication.getmContext().getResources().getColor(R.color.colorItemBg));
                ((TextView) itemView).setTextSize(12);

                int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, itemView.getResources().getDisplayMetrics()) + 0.5f);

                ((TextView) itemView).setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));
                ((TextView) itemView).setGravity(Gravity.CENTER);
            }
        }
    }
}