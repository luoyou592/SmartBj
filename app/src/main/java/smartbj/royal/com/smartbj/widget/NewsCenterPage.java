package smartbj.royal.com.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import smartbj.royal.com.smartbj.bean.CategoriBean;
import smartbj.royal.com.smartbj.net.GsonRequest;
import smartbj.royal.com.smartbj.net.NetworkListener;

/**
 * Created by 25505 on 2017/7/4.
 */

public class NewsCenterPage extends BaseTabPage {
    private List<CategoriBean.DataBean> mData = new ArrayList<>();

    public NewsCenterPage(Context context) {
        this(context, null);
    }

    public NewsCenterPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void loadDatafromService() {
        String url = "http://10.0.2.2:8080/zhbj/categories.json";
        //创建jsonrequest请求方式（gsonrequest继承jsonrequest）
        GsonRequest<CategoriBean> request = new GsonRequest<CategoriBean>(url,mListener,CategoriBean.class);
        //2.请求网络
        Volley.newRequestQueue(getContext()).add(request);
        //3.请求回调


    }


    private NetworkListener<CategoriBean> mListener = new NetworkListener<CategoriBean>(){
        //获取数据成功回调
        @Override
        public void onResponse(CategoriBean response) {
            Log.d("luoyou", "aa");
            Toast.makeText(getContext(),"result="+response.getExtend(),Toast.LENGTH_SHORT).show();
            CategoriBean dataBean = new CategoriBean();
            dataBean.setData(response.getData());
            mData = response.getData();
            //切换到新闻页面
            onMenuChange(0);
        }

        //获取数据失败回调
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onMenuChange(int position) {
        //TextView textView = new TextView(getContext());
        View view = null;
        switch (position) {
            case 0:  //代表新闻
                view = new NewsPage(getContext());
                break;
            case 1:  //代表专题
                TextView subject = new TextView(getContext());
                subject.setText("专题");
                view = subject;
                break;
            case 2:  //代表组图
                TextView photos = new TextView(getContext());
                photos.setText("组图");
                view = photos;
                break;
            case 3:  //代表互动
                TextView hudong = new TextView(getContext());
                hudong.setText("互动");
                view = hudong;
                break;
        }
        mTabFrame.removeAllViews();
        mTabFrame.addView(view);

    }

}
