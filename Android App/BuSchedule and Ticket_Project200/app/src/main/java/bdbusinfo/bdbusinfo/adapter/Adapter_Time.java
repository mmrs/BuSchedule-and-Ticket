package bdbusinfo.bdbusinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.seatplan.SeatPlan;

import android.util.Log;

/**
 * Created by siyam on 03-06-15.
 */
public class Adapter_Time extends BaseAdapter {

    Context context;
    ArrayList<String> items;
    String from, bus, typeST, timeST, responseString = "500 500",date;
    String link = null;
    public Adapter_Time(Context context, ArrayList<String> items, String from, String bus) {
        this.context = context;
        this.items = items;
        this.from = from;
        this.bus = bus;
    }

    @Override
    public int getCount() {
        return items.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_time, parent, false);
        }
        StringTokenizer sst = new StringTokenizer(items.get(position), "-");
        final TextView time = (TextView) convertView.findViewById(R.id.timeseatplan);
        final TextView type = (TextView) convertView.findViewById(R.id.type);

        timeST = sst.nextToken();
        time.setText("Time : " + timeST);
        if (sst.nextToken().contains("n"))
            typeST = "Non AC";
        else typeST = "AC";
        type.setText("Type : " + typeST);

        convertView.setOnClickListener(new View.OnClickListener() {

            String contactString = null;
            String fareString = null;

            @Override
            public void onClick(View view) {

                link = createLink(items.get(position));
                final Intent i = new Intent(context, SeatPlan.class);
                i.putExtra("from", from);
                i.putExtra("bus", bus);
                timeST = items.get(position);
                StringTokenizer sst = new StringTokenizer(timeST, "-");
                timeST = sst.nextToken();
                i.putExtra("time", timeST);
                if (sst.nextToken().contains("n"))
                    typeST = "Non AC";
                else typeST = "AC";
                i.putExtra("type", typeST);

                StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseString = response;
                        i.putExtra("flag", responseString);
                        i.putExtra("date",date);
                       // ((Activity) (context)).finish();
                        context.startActivity(i);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Could Not Contact With the Server", Toast.LENGTH_LONG).show();
                        i.putExtra("flag", responseString);
                       // ((Activity) (context)).finish();
                        context.startActivity(i);
                    }
                });
                RequestManager.getRequestQueue(context).add(request);
           //     Toast.makeText(context, "Fetching Data From Server", Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private String createLink(String s) {

        Date cal =  new Date();
        String date = cal.getYear()+1900+"";
        int m = cal.getMonth()+1;
        if(m<10) date+="0"+m;
        else date+=m;
         m = cal.getDate();
        if(m<10) date+="0"+m;
        else date+=m;
        this.date = date;
        String linkin = context.getString(R.string.server_link);
        linkin += "command=get" + "&from=" + from + "&bus=" + bus;
        s = s.replace(':','-');
        linkin+="&time="+ s ;
        linkin+="&date="+date;
        Log.i("link", linkin);
        return linkin;
    }
}
