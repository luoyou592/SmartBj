package smartbj.royal.com.smartbj.net;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/**
 * Created by 25505 on 2017/7/6.
 */

public class NetworkListener<T> implements Response.Listener<T>,ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(T response) {

    }
}
