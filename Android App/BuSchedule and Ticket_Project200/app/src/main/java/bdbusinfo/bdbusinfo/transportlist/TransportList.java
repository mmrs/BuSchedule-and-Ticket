package bdbusinfo.bdbusinfo.transportlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.adapter.Adapter_Transport;
import bdbusinfo.bdbusinfo.time.TimeActivity;

/**
 * Created by siyam on 02-06-15.
 */
public class TransportList extends Activity {

    private String from = "";
    private ArrayList<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportlist);
        Bundle allinfo = getIntent().getExtras();
        items = (ArrayList<String>) allinfo.get("transportlist");
        from =  allinfo.getString("from");
        Adapter_Transport adp = new Adapter_Transport(TransportList.this,items,from);
        ListView list = (ListView) findViewById(R.id.transportlistView);
        list.setAdapter(adp);
    }



    }
