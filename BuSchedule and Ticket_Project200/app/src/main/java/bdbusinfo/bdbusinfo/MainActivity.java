package bdbusinfo.bdbusinfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.about.AboutActivity;
import bdbusinfo.bdbusinfo.routelist.RouteListActivity;
import bdbusinfo.bdbusinfo.transportlist.TransportList;

public class MainActivity extends Activity{

    int flag = 0;
    Intent i;
    ArrayList<String> item = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setmainpage();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void finish() {
        if(flag==0){
            Toast.makeText(MainActivity.this,"tap again to exit",Toast.LENGTH_SHORT).show();
            flag =  1;
        }

        else {
            super.finish();
        }

    }

    public void setmainpage(){
    setContentView(R.layout.activity_main);

    Button exitbutton = (Button)findViewById(R.id.buttonexit);
    exitbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });


    final Button aboutbutton = (Button) findViewById(R.id.buttonabout);
    aboutbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this,AboutActivity.class);
            flag = 0;
            startActivity(i);
        }
    });

   Button routes = (Button) findViewById(R.id.buttonroutes);
    routes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flag = 0;
            sendRequest();
        }
    });
}
    void sendRequest(){

        StringRequest request = new StringRequest(StringRequest.Method.GET, createLink(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                i = new Intent(MainActivity.this,RouteListActivity.class);
                item.clear();
                StringTokenizer sst = new StringTokenizer(response," ");
                while(sst.hasMoreTokens())
                    item.add(sst.nextToken());
             //   Toast.makeText(MainActivity.this,response, Toast.LENGTH_SHORT).show();
                i.putExtra("routes", item);
               startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Could Not Contact With the Server", Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.getRequestQueue(MainActivity.this).add(request);
    }

    private String createLink() {
      //  String st = "http://192.168.127.1:8088/Project200_SeatStatus/Project200SeatStatus?";
        String st = getString(R.string.server_link);
        st+= "command=getRouteList";
        Log.i("link", st);
        return st;
    }
}
