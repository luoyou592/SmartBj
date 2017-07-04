package smartbj.royal.com.smartbj.activity;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.fragment.HomeFragment;
import smartbj.royal.com.smartbj.fragment.LeftSlidingMenu;

public class MainActivity extends SlidingFragmentActivity implements HomeFragment.OnTabChangeListener {

    private SlidingMenu mSlidingMenu;
    private HomeFragment mHomeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.left_menu);// 加载左边菜单布局
        setContentView(R.layout.right_content);  //加载右边内容
        mHomeFragment = new HomeFragment();
        initLeftSlidingMenu();
        initRightContainer();
        setListener();
    }

    //初始化侧滑菜单内容
    private void initLeftSlidingMenu() {
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //开启事务
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.left_menu, new LeftSlidingMenu())
                .commit();
    }

    //初始化右边内容区
    private void initRightContainer() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.right_content, mHomeFragment)
                .commit();

    }

    private void setListener() {
        mHomeFragment.setOnTabChangeListener(this);
    }

    @Override
    public void tabToggle(int checkedId) {
        if (checkedId==R.id.tab_home||checkedId==R.id.tab_setting){
            mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }else{
            mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        }
    }
}
