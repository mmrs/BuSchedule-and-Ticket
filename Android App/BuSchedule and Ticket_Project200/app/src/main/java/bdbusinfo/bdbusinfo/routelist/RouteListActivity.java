package bdbusinfo.bdbusinfo.routelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.adapter.Adapter_RouteList;
import bdbusinfo.bdbusinfo.adapter.Adapter_Transport;
import bdbusinfo.bdbusinfo.transportlist.TransportList;

/**
 * Created by siyam on 02-06-15.
 */
public class RouteListActivity extends Activity{

    ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routelist);
        Bundle allinfo = getIntent().getExtras();
        items = (ArrayList<String>) allinfo.get("routes");
        Adapter_RouteList adp = new Adapter_RouteList(RouteListActivity.this,items);
        ListView list = (ListView) findViewById(R.id.routelistlistView);
        list.setAdapter(adp);
    }
}
