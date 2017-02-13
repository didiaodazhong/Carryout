package com.peixing.carryout.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.ui.view.RecycleViewDivider;

import java.util.List;

public class LocationActivity extends BaseActivity implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener {
    private LinearLayout activityLocation;
    private Toolbar toolbarLocate;
    private MapView mapView;
    private RecyclerView recyclerLocate;
    private AMap aMap;
    private PoiSearch.Query query;
    private List<PoiItem> poiItems;// poi数据
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        activityLocation = (LinearLayout) findViewById(R.id.activity_location);
        toolbarLocate = (Toolbar) findViewById(R.id.toolbar_locate);
        recyclerLocate = (RecyclerView) findViewById(R.id.recycler_locate);
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        recyclerLocate.setLayoutManager(new LinearLayoutManager(MyApplication.getmContext(), LinearLayoutManager.VERTICAL, false));
        recyclerLocate.addItemDecoration(new RecycleViewDivider(MyApplication.getmContext(), LinearLayoutManager.HORIZONTAL, 6, 0XE3E0DC));

        if (aMap == null) {
            aMap = mapView.getMap();
            initMap();
        }
    }

    /**
     * 初始化地图设置
     */
    private void initMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                // 停止定位
                mlocationClient.stopLocation();
                // 查询附近信息
                MyApplication.LOCATION = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                doSearchQuery(aMapLocation.getCity());
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    private void doSearchQuery(String city) {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query("", "", city);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页

        if (MyApplication.LOCATION != null) {
            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(MyApplication.LOCATION, 500, true));//
            // 设置搜索区域为以lp点为圆心，其周围500米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiItems = result.getPois();
                    LocateAdapter locateAdapter = new LocateAdapter(poiItems, MyApplication.getmContext());
                    recyclerLocate.setAdapter(locateAdapter);
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public class LocateAdapter extends RecyclerView.Adapter<LocateAdapter.ViewHolder> {
        private List<PoiItem> poiItems;
        private Context context;

        public LocateAdapter(List<PoiItem> poiItems, Context context) {
            this.poiItems = poiItems;
            this.context = context;
        }

        @Override
        public LocateAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_select_receipt_address, viewGroup, false);
            return new LocateAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(LocateAdapter.ViewHolder itemViewHolder, int position) {

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
                        intent.putExtra("title", data.getTitle());
                        intent.putExtra("snippet", data.getSnippet());

                        LatLonPoint point = data.getLatLonPoint();
                        intent.putExtra("latitude", point.getLatitude());
                        intent.putExtra("longitude", point.getLongitude());
                        LocationActivity.this.setResult(200, intent);
                        LocationActivity.this.finish();
                    }
                });
            }
        }
    }

}
