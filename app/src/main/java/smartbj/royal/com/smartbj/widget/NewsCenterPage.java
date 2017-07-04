package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 25505 on 2017/7/4.
 */

public class NewsCenterPage extends BaseTabPage {
    public NewsCenterPage(Context context) {
        super(context);
    }

    public NewsCenterPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMenuChange(int position) {
        TextView textView = new TextView(getContext());
        switch (position) {
            case 0:  //代表新闻
                textView.setText("新闻");
                break;
            case 1:  //代表专题
                textView.setText("专题");
                break;
            case 2:  //代表组图
                textView.setText("组图");
                break;
            case 3:  //代表互动
                textView.setText("互动");
                break;
        }
        mTabFrame.removeAllViews();
        mTabFrame.addView(textView);
    }
}
