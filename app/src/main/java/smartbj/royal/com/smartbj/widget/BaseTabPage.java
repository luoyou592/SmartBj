package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;

/**
 * Created by 25505 on 2017/7/3.
 */

public class BaseTabPage extends RelativeLayout implements View.OnClickListener {
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tab_frame)
    FrameLayout mTabFrame;

    private OnTitleMenuClickListener mMenuClickListener;

    public BaseTabPage(Context context) {
        this(context, null);
    }

    public BaseTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_tab_page, this);
        ButterKnife.bind(this, this);
        setListener();
    }

    private void setListener() {
        mIvMenu.setOnClickListener(this);
    }

    /**
     * 提供设置标题方法
     *
     * @param text
     */
    public void setTitle(String text) {
        mTvTitle.setText(text);
    }

    public void hideMenu() {
        mIvMenu.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        //接口监听
        if (mMenuClickListener != null) {
            mMenuClickListener.switchMenu();
        }
    }
    public void onMenuChange(int position){

    }

    public interface OnTitleMenuClickListener {
        void switchMenu();
    }

    public void setOnTitleMenuClickListener(OnTitleMenuClickListener listener) {
        mMenuClickListener = listener;
    }

}
