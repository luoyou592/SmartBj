package smartbj.royal.com.smartbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 25505 on 2017/7/3.
 */

public class SharedPreUtil {
    private static final String SMART_BJ = "smart_bj"; //存储是否第一次启动
    //存储boolean值
    public static void saveBoolean(Context context, String keyName ,boolean value){
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(keyName,value).apply();
    }
    //获取boolean值
    public static boolean getBoolean(Context context,String keyName){
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(keyName,false);
    }



    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(SMART_BJ,Context.MODE_PRIVATE);
    }
}
