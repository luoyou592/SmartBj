package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.app.Constant;
import smartbj.royal.com.smartbj.bean.PhotosBean;
import smartbj.royal.com.smartbj.net.GsonRequest;
import smartbj.royal.com.smartbj.net.NetworkListener;

/**
 * Created by 25505 on 2017/7/7.
 */

public class PhotosPage extends RelativeLayout {
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    private List<PhotosBean.DataBean.NewsBean> mPhotosData;

    public PhotosPage(Context context) {
        this(context, null);
    }

    public PhotosPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.photos_page, this);
        ButterKnife.bind(this, this);
        init();
    }

    private void init() {
        mListView.setAdapter(mAdapter);
        mGridView.setAdapter(mAdapter);

    }

    public void setData(String url) {
        Log.d("luoyou", "photourl"+url);
        //获得url请求网络
        String photoUrl = Constant.HOST_URL + url;
        GsonRequest<PhotosBean> request = new GsonRequest<PhotosBean>(photoUrl, mPhotosBeanNetworkListener, PhotosBean.class);
        Volley.newRequestQueue(getContext()).add(request);

    }

    private NetworkListener<PhotosBean> mPhotosBeanNetworkListener = new NetworkListener<PhotosBean>() {
        @Override
        public void onResponse(PhotosBean response) {
            mPhotosData = response.getData().getNews();
            mAdapter.notifyDataSetChanged();
        }
    };

    private BaseAdapter mAdapter = new BaseAdapter(){

        @Override
        public int getCount() {
            if (mPhotosData == null) {
                return 0;

            }
            return mPhotosData.size();
        }

        @Override
        public Object getItem(int position) {
            return mPhotosData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photos, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mPhotosTitle.setText(mPhotosData.get(position).getTitle());
            Glide.with(getContext()).load(mPhotosData.get(position).getListimage())
                    .placeholder(R.mipmap.news_pic_default)
                    .into(holder.mPhotosImg);
            return convertView;
        }
    };
    //newsCenterPage传过来的点击切换事件
    public void setonSwitchView(boolean isList) {
        if (isList){
            mListView.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
        }else{
            mListView.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
        }
    }

    static class ViewHolder {
        @BindView(R.id.photos_img)
        ImageView mPhotosImg;
        @BindView(R.id.photos_title)
        TextView mPhotosTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
