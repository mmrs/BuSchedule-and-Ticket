package bdbusinfo.bdbusinfo.confirmticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.confirmnotification.ConfirmNotification;

import android.util.Log;

/**
 * Created by Siyam on 17-07-15.
 */
public class ConfirmTicket extends Activity{
    String from;
    String to;
    String bus;
    String time;
    String type;
    String seatNo;
    String passangerName;
    String mobile;
    String address;
    String responseString;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking);
        TextView fromT = (TextView) findViewById(R.id.confirmFrom);
        TextView toT =  (TextView) findViewById(R.id.confirmTo);
        TextView busT = (TextView) findViewById(R.id.confirmBus);
        TextView typeT = (TextView) findViewById(R.id.confirmType);
        TextView timeT = (TextView)findViewById(R.id.confirmTime);
        TextView seatNoT =  (TextView) findViewById(R.id.confirmSeat);
        TextView dateT = (TextView) findViewById(R.id.dateconfirmticket);
        final EditText nameT = (EditText)findViewById(R.id.confirmName);
        final EditText mobileT = (EditText) findViewById(R.id.confirmMobile);
        final EditText addressT = (EditText) findViewById(R.id.confirmAddress);
        Bundle allinfo = getIntent().getExtras();
        from = allinfo.getString("from");
        StringTokenizer sst = new StringTokenizer(from,"-");
        from = sst.nextToken();
        to = sst.nextToken();
        bus = allinfo.getString("bus");
        time = allinfo.getString("time");
        type = allinfo.getString("type");
        seatNo = allinfo.getString("seatno");
        responseString = allinfo.getString("flag");
        date = allinfo.getString("date");
        responseString+=seatNo;
        Log.i("tmp", responseString);
      //  createLink();
        fromT.setText("From : " + from.toUpperCase());
        toT.setText("To : " + to.toUpperCase());
        busT.setText("Bus : " + bus.toUpperCase());
        timeT.setText("Time : " + time.toUpperCase());
        typeT.setText("Type : " + type.toUpperCase());
        seatNoT.setText("Number of Seat : " + getSeats());
        dateT.setText("JourneyDate :" + new RequestManager().processDate(date));
        Button confirm = (Button)findViewById(R.id.confirmTicketButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passangerName = nameT.getText().toString();
                mobile  = mobileT.getText().toString();
                address = addressT.getText().toString();
              // Toast.makeText(ConfirmTicket.this,"-"+nameT.getText()+"-"+mobileT.getText()+"-"+addressT.getText()+"-",Toast.LENGTH_SHORT).show();
                if(passangerName.equals("") || mobile.equals(""))
                {
                     Toast.makeText(ConfirmTicket.this,"Enter Name,Mobile no & Address",Toast.LENGTH_SHORT).show();
                }
                 else  sendRequest();
            }
        });

    }

    private String getSeats() {
        StringTokenizer sst = new StringTokenizer(seatNo," ");
        int c = sst.countTokens();
        String ret = "";
        if(c<10)ret = "0";
        ret+= Integer.toString(c);
        return ret;
    }

    private void sendRequest() {
        String link = createLink();
        Log.i("test",link);
        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   Toast.makeText(ConfirmTicket.this, "Success! We Will Contact you Soon", Toast.LENGTH_LONG).show();
                Intent i = new Intent(ConfirmTicket.this, ConfirmNotification.class);
                i.putExtra("from",from);
                i.putExtra("to",to);
                i.putExtra("time",time);
                i.putExtra("type",type);
                i.putExtra("seatno",seatNo);
                i.putExtra("bus",bus);
                i.putExtra("name",passangerName);
                i.putExtra("address",address);
                i.putExtra("mobile",mobile);
                i.putExtra("date",date);
                startActivity(i);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConfirmTicket.this, "Could Not Contact With the Server", Toast.LENGTH_LONG).show();
            }
        });
        RequestManager.getRequestQueue(ConfirmTicket.this).add(request);
    }

     private String createLink() {

        String link = getString(R.string.server_link);
        link+="command=set"+"&from="+from+"-"+to+"&bus="+bus;
        time =  time.replace(':','-');
         link+="&time="+time;
        if(type.contains("n"))
            link+="-n";
         else link+="-y";

         link+="&date="+date;
        StringTokenizer tokenizer = new StringTokenizer(responseString," ");
        while(tokenizer.hasMoreElements()){
            link+="&no="+tokenizer.nextToken();
        }
        Log.i("link",link);
         return link;
    }
}
