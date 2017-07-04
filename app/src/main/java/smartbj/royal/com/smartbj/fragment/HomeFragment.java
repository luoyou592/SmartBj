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


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        BaseTabPage tabPage;
        if (mTabPageCache.get(checkedId) != null) {
            tabPage = mTabPageCache.get(checkedId);
        } else {
            tabPage = creatBasePage(checkedId);

            mTabPageCache.put(checkedId,tabPage);
        }
        //添加之前清除之前数据，保存只缓存一个
        mPageContainer.removeAllViews();
        mPageContainer.addView(tabPage);
        if (mTabChangeListener!=null){
            mTabChangeListener.tabToggle(checkedId);
        }
    }

    /*
     *创建BasePage
     */
    private BaseTabPage creatBasePage(int checkedId) {
        BaseTabPage tabPage = new BaseTabPage(getContext());
        switch (checkedId) {
            case R.id.tab_home:
                tabPage.setTitle("首页");
                tabPage.hideMenu();
                break;
            case R.id.tab_news:
                tabPage.setTitle("新闻中心");
                break;
            case R.id.tab_smart:
                tabPage.setTitle("智慧");
                break;
            case R.id.tab_gov:
                tabPage.setTitle("政务");
                break;
            case R.id.tab_setting:
                tabPage.setTitle("设置中心");
                tabPage.hideMenu();
                break;
        }
        return tabPage;
    }
    public interface OnTabChangeListener{
        void tabToggle(int checkedId);
    }
    public void setOnTabChangeListener(OnTabChangeListener listener){
        mTabChangeListener = listener;
    }
}
