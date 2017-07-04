package smartbj.royal.com.smartbj.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.app.Constant;
import smartbj.royal.com.smartbj.utils.SharedPreUtil;

/**
 * Created by 25505 on 2017/7/3.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    @BindView(R.id.view_pager_guide)
    ViewPager mViewPager;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.indicator)
    CirclePageIndicator mIndicator;
    private Context mContext;
    private int[] mPages = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void init() {
        mContext = this;
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
        setListener();
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(this);
        mBtnStart.setOnClickListener(this);
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mPages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(mPages[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    //viewpager 监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == mPages.length - 1) {
            mBtnStart.setVisibility(View.VISIBLE);
        } else {
            mBtnStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        SharedPreUtil.saveBoolean(mContext, Constant.FIRST_START,true);
        startActivity(MainActivity.class);
        finish();
    }
}
