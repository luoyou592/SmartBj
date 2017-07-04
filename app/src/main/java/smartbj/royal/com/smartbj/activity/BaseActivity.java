package smartbj.royal.com.smartbj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by 25505 on 2017/7/3.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
    }

    protected void init(){

    }

    public abstract int getLayoutId() ;
    protected void startActivity(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
