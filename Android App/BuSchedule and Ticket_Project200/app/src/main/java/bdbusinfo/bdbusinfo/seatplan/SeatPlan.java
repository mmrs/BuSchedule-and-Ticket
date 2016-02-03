package bdbusinfo.bdbusinfo.seatplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.adapter.Adapter_SeatPlan;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.confirmticket.ConfirmTicket;
import bdbusinfo.bdbusinfo.pickdate.PickDate;

public class SeatPlan extends Activity{

    String from,bus,time,type,price ="Fare: " +"Not Defined",date;
    String responseString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_plan);
        Bundle allInfo = getIntent().getExtras();
        from = allInfo.getString("from");
        bus = allInfo.getString("bus");
        time = allInfo.getString("time");
        type = allInfo.getString("type");
        responseString = allInfo.getString("flag");
        date = allInfo.getString("date");
        TextView routeTextView = (TextView) findViewById(R.id.routeTextView);
        routeTextView.setText(from.toUpperCase());
        TextView busTextView = (TextView)findViewById(R.id.busTextView);
        busTextView.setText(bus.toUpperCase());
        TextView timeTextView = (TextView) findViewById(R.id.timeseatplan);
        timeTextView.setText(time.toUpperCase() + "-" + type.toUpperCase());
        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        if(!bus.equals("GREENLINE"))
            price = "Fare: " +"470 tk/seat";
        if(!type.contains("n"))
            price = "Fare: " +"800 tk/seat";
        if(bus.equals("GREENLINE"))
            price = "Fare: " +"1000 tk/seat";
        priceTextView.setText(price);
        ListView seatListView = (ListView)findViewById(R.id.seatlistview);
        final Adapter_SeatPlan adapter_seatPlan = new Adapter_SeatPlan(SeatPlan.this, from, bus, time, type,responseString);
       // seatListView.setAdapter(new Adapter_SeatPlan(SeatPlan.this, from, bus, time, type,responseString));
        seatListView.setAdapter(adapter_seatPlan);
       // Toast.makeText(getApplicationContext(), from + bus + time + type + price, Toast.LENGTH_SHORT);
        Button nextToConfirm = (Button) findViewById(R.id.nexttoconfirm);
        nextToConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeatPlan.this, ConfirmTicket.class);
                i.putExtra("from", from);
                i.putExtra("bus", bus);
                i.putExtra("time", time);
                i.putExtra("type", type);
                i.putExtra("flag",responseString);
                i.putExtra("date",date);
                String seatNo = adapter_seatPlan.getSeats();
                i.putExtra("seatno",seatNo);
             //   i.putExtra("seatno", seatNo);
                //   Toast.makeText(context, seatNo, Toast.LENGTH_SHORT).show();
                StringTokenizer sst = new StringTokenizer(seatNo," ");
                int c = sst.countTokens();
                if(c>0){
                    finish();
                    startActivity(i);
                }
                else if(c==0)
                    Toast.makeText(SeatPlan.this,"no seat selected",Toast.LENGTH_SHORT).show();

            }
        });
        Button changedate = (Button)findViewById(R.id.changedate);
        changedate.setText((CharSequence) "Date:\n" + new RequestManager().processDate(date));
        changedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeatPlan.this, PickDate.class);
                i.putExtra("from", from);
                i.putExtra("bus", bus);
                i.putExtra("time", time);
                i.putExtra("type", type);
                finish();
                startActivity(i);

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seatplanmeu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
