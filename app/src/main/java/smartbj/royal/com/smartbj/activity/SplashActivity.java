package smartbj.royal.com.smartbj.activity;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.app.Constant;
import smartbj.royal.com.smartbj.utils.SharedPreUtil;

/**
 * Created by 25505 on 2017/7/3.
 */

public class SplashActivity extends BaseActivity implements Animation.AnimationListener {
    private static final int DURATION = 1000;
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    private Context mContext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        mContext = this;


        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(false);
        set.setDuration(DURATION);
        set.addAnimation(ra);
        set.addAnimation(sa);
        set.addAnimation(aa);
        mIvSplash.startAnimation(set);
        //动画监听
        set.setAnimationListener(this);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //判断是否进入过
        boolean isFirst = SharedPreUtil.getBoolean(mContext, Constant.FIRST_START);
        if (isFirst) {
            startActivity(MainActivity.class);
        } else {
            startActivity(GuideActivity.class);
        }
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
