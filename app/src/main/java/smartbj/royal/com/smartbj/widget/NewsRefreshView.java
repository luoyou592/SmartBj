package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.itheima.pulltorefreshlib.PullToRefreshBase;
import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.leon.loopviewpagerlib.FunBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.activity.NewsDetailActivity;
import smartbj.royal.com.smartbj.app.Constant;
import smartbj.royal.com.smartbj.bean.NewsListBean;
import smartbj.royal.com.smartbj.net.GsonRequest;
import smartbj.royal.com.smartbj.net.NetworkListener;
import smartbj.royal.com.smartbj.utils.SharedPreUtil;

/**
 * Created by 25505 on 2017/7/6.
 */

public class NewsRefreshView extends PullToRefreshListView {
    private FunBanner mFunBanner;
    private NewsAdapter mAdapter;
    private int[] imgIds = new int[]{R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d};
    private List<NewsListBean.DataBean.NewsBean> mNewsData;
    private String mNewsUrl;
    private String mMoreUrl;

    public NewsRefreshView(Context context) {
        this(context, null);
    }

    public NewsRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.news_refresh_view, null);
        //老师集成的轮播图
        FunBanner.Builder builder = new FunBanner.Builder(getContext());
        mFunBanner = builder.setEnableAutoLoop(true)
                .setImageResIds(imgIds)
                .setDotRadius(getResources().getDimensionPixelSize(R.dimen.dot_radius))
                .setDotSelectedColor(Color.RED)
                .setHeightWidthRatio(0.5f)
                .setIndicatorBarHeight(getResources().getDimensionPixelSize(R.dimen.bar_height))
                .setIndicatorBackgroundColor(R.color.indicator_bg)
                .build();
        getRefreshableView().addHeaderView(mFunBanner);
        mAdapter = new NewsAdapter();
        setAdapter(mAdapter);
        setMode(Mode.BOTH);
        //刷新监听
        setOnRefreshListener(mRefreshListener2);
        //点击监听
        setOnItemClickListener(mItemClickListener);
    }
    /**
     * 上拉下拉刷新监听
     */
    private OnRefreshListener2 mRefreshListener2 = new OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
            refreshNewsList();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
            if (mMoreUrl.length()>0){
                loadMoreNews();
            }else {
                Toast.makeText(getContext(),"无更多数据",Toast.LENGTH_SHORT).show();
                //隐藏头,一定的延时
                //onRefreshComplete();
                post(new Runnable() {
                    @Override
                    public void run() {
                        onRefreshComplete();
                    }
                });
            }

        }
    };
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         //NewsRefreshView列表点击新闻列表position不是从0开始，因为NewsRefreshView还包含了两个头（刷新头，轮播头）
            position = position-2;
            //记录已点击的新闻列表 (每个新闻条目id都不同，利用id存储)
            SharedPreUtil.saveBoolean(getContext(),mNewsData.get(position).getId()+"",true);
            Intent intent = new Intent(getContext(),NewsDetailActivity.class);
            intent.putExtra("url",mNewsData.get(position).getUrl());
            getContext().startActivity(intent);
            mAdapter.notifyDataSetChanged();
        }
    };

    /**
     * @param url-缺少主路径
     * 补主机路径 HOST_URL
     */
    //获得url请求网络数据（新闻列表）
    public void setUrl(String url) {
        mNewsUrl = Constant.HOST_URL+url;
        GsonRequest<NewsListBean> request = new GsonRequest<NewsListBean>(mNewsUrl,mNetworkListener,NewsListBean.class);
        Volley.newRequestQueue(getContext()).add(request);
    }
    //进入页面请求网络回调
    private NetworkListener<NewsListBean> mNetworkListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onResponse(NewsListBean response) {
            mNewsData = response.getData().getNews();
            mMoreUrl = response.getData().getMore();
            mAdapter.notifyDataSetChanged();
            List<NewsListBean.DataBean.TopnewsBean> topnews = response.getData().getTopnews();
            List<String> urls = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for(int i=0;i<topnews.size();i++){
                urls.add(topnews.get(i).getTopimage());
                titles.add(topnews.get(i).getTitle());
            }
            mFunBanner.setImageUrlsAndTitles(urls,titles);

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("luoyou", "error="+error);
        }
    };


    /**
     * 下拉刷新新闻列表
     */
    private void refreshNewsList() {
        GsonRequest<NewsListBean> request = new GsonRequest<NewsListBean>(mNewsUrl,mPullDownListener,NewsListBean.class);
        Volley.newRequestQueue(getContext()).add(request);
    }
    //下拉请求网络回调
    private NetworkListener<NewsListBean> mPullDownListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onResponse(NewsListBean response) {
            Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
            onRefreshComplete();
            mMoreUrl = response.getData().getMore();
        }
    };

    /**
     * 上啦加载更多
     */
    private void loadMoreNews() {
        mMoreUrl = Constant.HOST_URL+mMoreUrl;
        GsonRequest<NewsListBean> request = new GsonRequest<NewsListBean>(mMoreUrl,mPullUpListener,NewsListBean.class);
        Volley.newRequestQueue(getContext()).add(request);
    }

    /**
     * 上拉加载更多请求网络回调
     */
    private NetworkListener<NewsListBean> mPullUpListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onResponse(NewsListBean response) {
            List<NewsListBean.DataBean.NewsBean> news = response.getData().getNews();
            mNewsData.addAll(news);
            mAdapter.notifyDataSetChanged();
            onRefreshComplete();
            mMoreUrl = response.getData().getMore();  //每次加载数据须更新moreUrl数据
        }
    };


    private class NewsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //TODO
            if (mNewsData==null){
                return 0;
            }
            return mNewsData.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView = View.inflate(getContext(), R.layout.item_news_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTvNewsTitle.setText(mNewsData.get(position).getTitle());
            holder.mTvPubDate.setText(mNewsData.get(position).getPubdate());
            //判断条目是否有被点击过
            boolean isScan = SharedPreUtil.getBoolean(getContext(), mNewsData.get(position).getId() + "");
            if (isScan){
                holder.mTvNewsTitle.setTextColor(Color.GRAY);
                holder.mTvPubDate.setTextColor(Color.GRAY);
            }else{
                holder.mTvNewsTitle.setTextColor(Color.BLACK);
                holder.mTvPubDate.setTextColor(Color.BLACK);
            }


            Glide.with(getContext()).load(mNewsData.get(position).getListimage())
                    .placeholder(R.mipmap.news_pic_default)
                    .into(holder.mIvNewsIcon);
            return convertView;

        }

    }

    static class ViewHolder {
        @BindView(R.id.iv_news_icon)
        ImageView mIvNewsIcon;
        @BindView(R.id.tv_news_title)
        TextView mTvNewsTitle;
        @BindView(R.id.tv_pub_date)
        TextView mTvPubDate;
        @BindView(R.id.iv_comment_icon)
        ImageView mIvCommentIcon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}

