package bdbusinfo.bdbusinfo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.confirmticket.*;

import bdbusinfo.bdbusinfo.seatplan.SeatPlan;

/**
 * Created by Siyam on 14-07-15.
 */
public class Adapter_SeatPlan extends BaseAdapter implements View.OnClickListener {

    Context context;
    String route, bus, time, type,responseString="";
    static String ret=null;
    int flag[] = new int[45];
    public Adapter_SeatPlan(Context context, String route, String bus, String time, String type,String responseString) {
        this.context = context;
        this.route = route;
        this.bus = bus;
        this.time = time;
        this.type = type;
        for(int k=0 ;k<45;k++)
            flag[k] = 0;
        this.responseString =responseString;
        processResponseString(responseString);
    }

    private void processResponseString(String responseString) {
        StringTokenizer tokenizer = new StringTokenizer(responseString," ");
        while(tokenizer.hasMoreElements()){
            int pos = Integer.parseInt(tokenizer.nextToken());
            if(pos<=40)
            flag[pos] = 2;
        }
    }
    @Override

    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.eachrowseatview, viewGroup, false);
        }
        Button button1 = (Button) view.findViewById(R.id.seat1);
        Button button2 = (Button) view.findViewById(R.id.seat2);
        Button button3 = (Button) view.findViewById(R.id.seat3);
        Button button4 = (Button) view.findViewById(R.id.seat4);
        char pos = (char) (i + 65);
        button1.setText(pos + "1");
        button2.setText(pos + "2");
        button3.setText(pos + "3");
        button4.setText(pos + "4");
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        if(flag[4*i+1] == 2)
        button1.setBackgroundResource(R.drawable.red);
        if(flag[4*i+2] == 2)
            button2.setBackgroundResource(R.drawable.red);
        if(flag[4*i+3] == 2)
            button3.setBackgroundResource(R.drawable.red);
        if(flag[4*i+4] == 2)
            button4.setBackgroundResource(R.drawable.red);
        if(flag[4*i+1]==0)
            button1.setBackgroundResource(R.drawable.blue);
        if(flag[4*i+2]==0)
            button2.setBackgroundResource(R.drawable.blue);
        if(flag[4*i+3]==0)
            button3.setBackgroundResource(R.drawable.blue);
        if (flag[4*i+4]==0)
            button4.setBackgroundResource(R.drawable.blue);

        return view;
    }

    @Override
    public void onClick(View view) {
        int iid = view.getId();
        Button bbt = (Button) view.findViewById(iid);
        String seatNo = (String) bbt.getText();
        int k = seatNo.charAt(0) - 65;
        int tmp = k*4 + (int)seatNo.charAt(1)-48;
        if(flag[tmp]==2){
            Toast.makeText(context,"Already Booked",Toast.LENGTH_SHORT).show();
        }
        else if(flag[tmp]==0){
            bbt.setBackgroundResource(R.drawable.green1);
            flag[tmp]=1;
        }
        else if(flag[tmp]==1) {
            bbt.setBackgroundResource(R.drawable.blue);
            flag[tmp] = 0;
        }

    }

    public String getSeats(){
        String res= "";
        for(int i=1;i<=40;i++){
            if(flag[i]==1){
                res = res + Integer.toString(i)+" ";
            }
        }
        return res;
    }
}
