package smartbj.royal.com.smartbj.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import smartbj.royal.com.smartbj.R;
import smartbj.royal.com.smartbj.app.Constant;
import smartbj.royal.com.smartbj.utils.SharedPreUtil;

/**
 * Created by 25505 on 2017/7/7.
 */

public class NewsDetailActivity extends BaseActivity {
    @BindView(R.id.detail_back)
    ImageView mDetailBack;
    @BindView(R.id.detail_share)
    ImageView mDetailShare;
    @BindView(R.id.detail_webview)
    WebView mWebview;
    @BindView(R.id.detail_text_size)
    ImageView mDetailTextSize;
    private WebSettings mWebSettings;
    private String[] mAlerItems = {"最小","较小","正常","较大","最大"};
    private int checkedItem = 2;//默认正常
    private Context mContext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init() {
        mContext = this;
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        int textSize = SharedPreUtil.getInt(this, Constant.TEXT_SIZE);
        intTosize(textSize);
        checkedItem = textSize;
        mWebview.loadUrl(url);
    }

    @OnClick({R.id.detail_back, R.id.detail_share,R.id.detail_text_size})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.detail_share:
                showShare();
                break;
            case R.id.detail_text_size:
                setTextSize();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
    private void setTextSize() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("字体大小设置")
                .setSingleChoiceItems(mAlerItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreUtil.saveInt(mContext, Constant.TEXT_SIZE,which);
                        intTosize(which);
                        checkedItem = which;
                        dialog.dismiss();
                    }
                });
        builder.show();

    }

    private void intTosize(int which) {
        switch (which) {
            case 0:
                mWebSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
            case 1:
                mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 2:
                mWebSettings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                mWebSettings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 4:
                mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
                break;
        }
    }

}
