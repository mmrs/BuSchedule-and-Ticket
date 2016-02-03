package bdbusinfo.bdbusinfo.time;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.adapter.Adapter_Time;


/**
 * Created by siyam on 03-06-15.
 */
public class TimeActivity extends Activity{

    private String from = "",bus="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ListView list = (ListView)findViewById(R.id.listviewoftimelist);
        Bundle allinfo = getIntent().getExtras();
        from = allinfo.getString("from");
        bus = allinfo.getString("bus");
        ArrayList time = (ArrayList) allinfo.get("time");
                list.setAdapter(new Adapter_Time(TimeActivity.this, time, from, bus));

        }
    }
