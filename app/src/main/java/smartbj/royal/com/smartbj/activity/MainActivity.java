package smartbj.royal.com.smartbj.activity;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.fragment.HomeFragment;
import smartbj.royal.com.smartbj.fragment.LeftSlidingMenu;

public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu mSlidingMenu;
    private HomeFragment mHomeFragment;
    private LeftSlidingMenu mLeftSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.left_menu);// 加载左边菜单布局
        setContentView(R.layout.right_content);  //加载右边内容
        mHomeFragment = new HomeFragment();
        mLeftSlidingMenu = new LeftSlidingMenu();
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
                .replace(R.id.left_menu, mLeftSlidingMenu)
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
        //mHomeFragment接口监听
        mHomeFragment.setOnTabChangeListener(new HomeFragment.OnTabChangeListener() {
            //控制不同页面的滑动情况
            @Override
            public void tabToggle(int checkedId) {
                if (checkedId==R.id.tab_home||checkedId==R.id.tab_setting){
                    mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }else{
                    mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                }
            }
            //点击标题栏菜单按钮切换
            @Override
            public void menuSwitch() {
                getSlidingMenu().toggle();
            }
        });
        //mLeftSlidingMenu接口监听
        mLeftSlidingMenu.setOnLeftMenuChangeListener(new LeftSlidingMenu.OnLeftMenuChangeListener() {
            //点击左边菜单切换
            @Override
            public void OnLeftMenuChange(int position) {
                //打开或者关闭侧滑菜单
                getSlidingMenu().toggle();

               mHomeFragment.OnMenuChange(position);
            }
        });
    }



}
