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

/**
 * Created by Siyam on 06-Sep-15.
 */
public class Adapter_Transport extends BaseAdapter implements View.OnClickListener{

    ArrayList<String> items = new ArrayList<>();
    Context context;
    String from = "";
    String bus = "";
    Intent i;

    public Adapter_Transport(Context context,ArrayList<String> items,String from) {
        this.items = items;
        this.context = context;
        this.from = from;
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
        i = new Intent(context, TimeActivity.class);
        i.putExtra("from",from);
        Button bbt = (Button) v.findViewById(v.getId());
        bus = bbt.getText().toString();
        i.putExtra("bus",bus);

        sendRequest();

    }

    void sendRequest(){
        bus = from+"-"+bus;
        final String  link = context.getString(R.string.server_link) + "command=getSchedule" + "&bus="+bus;
        StringRequest request = new StringRequest(Request.Method.GET,link,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                StringTokenizer sst = new StringTokenizer(response," ");
                ArrayList<String> list = new ArrayList<>();
                while(sst.hasMoreTokens()){
                    list.add(sst.nextToken());
                }
                Log.i("link", link);
                i.putExtra("time", list);
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



