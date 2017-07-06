package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.bean.CategoriBean;

/**
 * Created by 25505 on 2017/7/6.
 */

public class NewsPage extends RelativeLayout {
    @BindView(R.id.indicator)
    TabPageIndicator mIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private String[] mDatas = {"新闻","体育","鱼儿","娱乐","头条","茶余","饭后","电影","体育","鱼儿","娱乐"};
    private List<CategoriBean.DataBean.ChildrenBean> mChildren = new ArrayList<>();

    public NewsPage(Context context) {
        this(context, null);
    }

    public NewsPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.news_page, this);
        ButterKnife.bind(this, this);
        setData();
        init();
    }

    private void setData() {
        CategoriBean.DataBean dataBean = new CategoriBean.DataBean();
        mChildren = dataBean.getChildren();
        /*mIndicator.notifyDataSetChanged();
        mPagerAdapter.notifyDataSetChanged();*/
    }

    private void init() {

        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
    }


    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public CharSequence getPageTitle(int position) {

            //return mDatas[position];
            return mChildren.get(position).getTitle();
        }

        @Override
        public int getCount() {
            //return mDatas.length;
            return mChildren.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*TextView textView = new TextView(getContext());
            textView.setText(mDatas[position]);
            container.addView(textView);*/
            NewsRefreshView refreshView = new NewsRefreshView(getContext());
            container.addView(refreshView);
            return refreshView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
}
