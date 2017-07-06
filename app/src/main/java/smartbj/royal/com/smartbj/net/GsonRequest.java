package smartbj.royal.com.smartbj.net;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * 封装请求方法工具类
 * Created by 25505 on 2017/7/6.
 */

public class GsonRequest<T> extends JsonRequest<T> {
    private Class<T> mClass;
    private Gson mGson;

    public GsonRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener,Class<T> clazz) {
        super(method, url, requestBody, listener, errorListener);
        mClass = clazz;
    }

    public GsonRequest(String url, NetworkListener listener, Class<T> clazz) {
        this(Method.GET, url, null, listener,listener,clazz);


    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("luoyou", "bb");
            String result = new String(response.data,PROTOCOL_CHARSET);
            mGson = new Gson();
            T t = mGson.fromJson(result, mClass);
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            return Response.success(t,cacheEntry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
}

}
