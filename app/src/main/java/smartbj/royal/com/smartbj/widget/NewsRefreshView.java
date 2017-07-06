package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.leon.loopviewpagerlib.FunBanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;

/**
 * Created by 25505 on 2017/7/6.
 */

public class NewsRefreshView extends PullToRefreshListView {
    private FunBanner mFunBanner;
    private NewsAdapter mAdapter;
    private int[] imgIds = new int[]{R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d};

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
    }

    private class NewsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //TODO
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
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
                //TODO
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

    }

    static class ViewHolder {
        @BindView(R.id.iv_news_icon)
        ImageView mIvNewsIcon;
        @BindView(R.id.tv_news_title)
        TextView mTvNewsTitle;
        @BindView(R.id.tv_news_date)
        TextView mTvNewsDate;
        @BindView(R.id.iv_comment_icon)
        ImageView mIvCommentIcon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}

