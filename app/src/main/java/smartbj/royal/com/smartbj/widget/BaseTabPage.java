package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;

/**
 * Created by 25505 on 2017/7/3.
 */

public class BaseTabPage extends RelativeLayout {
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public BaseTabPage(Context context) {
        this(context, null);
    }

    public BaseTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_tab_page, this);
        ButterKnife.bind(this, this);

    }

    /**
     * 提供设置标题方法
     * @param text
     */
    public void setTitle(String text){
        mTvTitle.setText(text);
    }
    public void hideMenu(){
        mIvMenu.setVisibility(View.GONE);
    }


}
