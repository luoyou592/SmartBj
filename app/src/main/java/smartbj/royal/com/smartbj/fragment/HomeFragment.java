package smartbj.royal.com.smartbj.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.widget.BaseTabPage;
import smartbj.royal.com.smartbj.widget.GovAffairsTabPage;
import smartbj.royal.com.smartbj.widget.HomeTabPage;
import smartbj.royal.com.smartbj.widget.NewsCenterPage;
import smartbj.royal.com.smartbj.widget.SettingTabPage;
import smartbj.royal.com.smartbj.widget.SmartServiceTabPage;

/**
 * Created by 25505 on 2017/7/3.
 */

public class HomeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.page_container)
    FrameLayout mPageContainer;
    private SparseArray<BaseTabPage> mTabPageCache = new SparseArray<>();
    private OnTabChangeListener mTabChangeListener;
    private BaseTabPage mCurrentTabPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.home_frame, null);
        ButterKnife.bind(this, contentView);
        init();
        return contentView;
    }

    private void init() {
        mRadioGroup.setOnCheckedChangeListener(this);
        //默认进入就选中首页
        mRadioGroup.check(R.id.tab_home);
    }
    //作为mainactivity传数据过来的方法
    public void OnMenuChange(int position){
        mCurrentTabPage.onMenuChange(position);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        mCurrentTabPage = null;
        if (mTabPageCache.get(checkedId) != null) {
            mCurrentTabPage = mTabPageCache.get(checkedId);
        } else {
            mCurrentTabPage = creatBasePage(checkedId);

            mTabPageCache.put(checkedId, mCurrentTabPage);
        }
        //添加之前清除之前数据，保存只缓存一个

        mPageContainer.removeAllViews();
        mPageContainer.addView(mCurrentTabPage);
        if (mTabChangeListener!=null){
            mTabChangeListener.tabToggle(checkedId);
        }
        mCurrentTabPage.setOnTitleMenuClickListener(new BaseTabPage.OnTitleMenuClickListener() {
            @Override
            public void switchMenu() {
                if (mTabChangeListener!=null){
                    mTabChangeListener.menuSwitch();
                }
            }
        });
    }

    /*
     *创建BasePage
     */
    private BaseTabPage creatBasePage(int checkedId) {
        BaseTabPage tabPage = null;
        switch (checkedId) {
            case R.id.tab_home:
                tabPage = new HomeTabPage(getContext());
                tabPage.setTitle("首页");
                tabPage.hideMenu();
                break;
            case R.id.tab_news:
                tabPage = new NewsCenterPage(getContext());
                tabPage.setTitle("新闻中心");
                break;
            case R.id.tab_smart:
                tabPage = new SmartServiceTabPage(getContext());
                tabPage.setTitle("智慧");
                break;
            case R.id.tab_gov:
                tabPage = new GovAffairsTabPage(getContext());
                tabPage.setTitle("政务");
                break;
            case R.id.tab_setting:
                tabPage = new SettingTabPage(getContext());
                tabPage.setTitle("设置中心");
                tabPage.hideMenu();
                break;
        }
        //发送网络请求
        tabPage.loadDatafromService();
        return tabPage;
    }


    public interface OnTabChangeListener{
        void tabToggle(int checkedId);
        void menuSwitch();
    }
    public void setOnTabChangeListener(OnTabChangeListener listener){
        mTabChangeListener = listener;
    }
}
