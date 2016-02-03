package bdbusinfo.bdbusinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.time.TimeActivity;
import bdbusinfo.bdbusinfo.transportlist.TransportList;

/**
 * Created by Siyam on 07-Sep-15.
 */
public class Adapter_RouteList extends BaseAdapter implements View.OnClickListener{

    ArrayList<String> items = new ArrayList<>();
    Context context;
    String from;
    Intent i;

    public Adapter_RouteList(Context context,ArrayList<String> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_transportlist, parent, false);
        }
        Button bbt = (Button) convertView.findViewById(R.id.transportname);
        bbt.setText(items.get(position));
        bbt.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        i = new Intent(context, TransportList.class);
        Button bbt = (Button) v.findViewById(v.getId());
        from = bbt.getText().toString();
        i.putExtra("from",from);
        sendRequest();
    }

    void sendRequest(){
        final String  link = context.getString(R.string.server_link) + "command=getTransportList" + "&bus=" + from;
        StringRequest request = new StringRequest(Request.Method.GET,link,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                StringTokenizer sst = new StringTokenizer(response," ");
                ArrayList<String> list = new ArrayList<>();
                while(sst.hasMoreTokens()){
                    list.add(sst.nextToken());
                }
                Log.i("link", link);
                i.putExtra("transportlist", list);
             //   Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Could Not Contact With the Server", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestManager.getRequestQueue(context).add(request);
    }
}




