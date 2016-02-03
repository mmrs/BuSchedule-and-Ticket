package bdbusinfo.bdbusinfo.pickdate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.seatplan.SeatPlan;

public class PickDate extends Activity implements View.OnClickListener{

    String from,bus,type,time,date;
    private Intent i;

     DatePicker dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickdate);
        dp = (DatePicker) findViewById(R.id.datePicker);
        Button ok = (Button) findViewById(R.id.okbutton);
        ok.setOnClickListener(this);
        Bundle allinfo = getIntent().getExtras();
        from = allinfo.getString("from");
        bus = allinfo.getString("bus");
        time = allinfo.getString("time");
        type = allinfo.getString("type");
    }

    @Override
    public void onClick(View v) {


        i = new Intent(PickDate.this, SeatPlan.class);
        StringRequest request = new StringRequest(Request.Method.GET, createLink(time), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                i.putExtra("flag", response);
                i.putExtra("from",from);
                i.putExtra("bus",bus);
                i.putExtra("time", time);
                i.putExtra("type",type);
                i.putExtra("date",date);
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PickDate.this, "Could Not Contact With the Server", Toast.LENGTH_LONG).show();
            }
        });
        RequestManager.getRequestQueue(PickDate.this).add(request);

    }

    private String createLink(String s) {

        String date = dp.getYear()+"";
        int m = dp.getMonth()+1;
        if(m<10) date+="0"+m;
        else date+=m;
        m = dp.getDayOfMonth();
        if(m<10) date+="0"+m;
        else date+=m;
        this.date = date;
        String linkin = PickDate.this.getString(R.string.server_link);
        linkin += "command=get" + "&from=" + from + "&bus=" + bus;
        s = s.replace(' ','-');
        s = s.replace(':','-');
        linkin+="&time="+ s ;
        if(type.contains("n"))
        linkin+="-n";
        else linkin+= "-y";
        linkin+="&date="+date;
        Log.i("link", linkin);
        return linkin;
    }
}
