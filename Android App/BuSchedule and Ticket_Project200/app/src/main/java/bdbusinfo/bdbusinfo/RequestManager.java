package bdbusinfo.bdbusinfo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Siyam on 15-Aug-15.
 */
public class RequestManager {

    private static RequestQueue queue;
     String s,ret;

    public static RequestQueue getRequestQueue(Context context){
        if(queue == null){
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public String processDate(String date)
    {

        ret = date.substring(6,8) + "/";
        ret+= date.substring(4,6) + "/";
        ret+= date.substring(0,4);

        return ret;
    }
}
