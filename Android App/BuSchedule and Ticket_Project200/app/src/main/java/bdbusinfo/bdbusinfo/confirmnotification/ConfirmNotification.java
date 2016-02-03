package bdbusinfo.bdbusinfo.confirmnotification;

import android.app.Activity;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;

import bdbusinfo.bdbusinfo.R;
import bdbusinfo.bdbusinfo.RequestManager;
import bdbusinfo.bdbusinfo.confirmticket.ConfirmTicket;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;

import android.content.Intent;
import android.widget.Toast;

public class ConfirmNotification extends Activity  implements View.OnClickListener{

    RelativeLayout L1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_notification);
        L1 = (RelativeLayout) findViewById(R.id.cnid);
        Bundle allinfo = getIntent().getExtras();
        String name = allinfo.getString("name");
        String from = allinfo.getString("from");
        String to = allinfo.getString("to");
        String time = allinfo.getString("time");
        String type = allinfo.getString("type");
        String bus = allinfo.getString("bus");
        String mobile = allinfo.getString("mobile");
        String seatno = allinfo.getString("seatno");
        String address = allinfo.getString("address");
        String date = allinfo.getString("date");
        ((TextView) findViewById(R.id.namecn)).setText("Name : " + name.toUpperCase());
        ((TextView) findViewById(R.id.fromcn)).setText("From : " + from.toUpperCase());
        ((TextView) findViewById(R.id.tocn)).setText("To : " + to.toUpperCase());
        ((TextView) findViewById(R.id.mobilecn)).setText("Mobile : " + mobile);
        ((TextView) findViewById(R.id.addresscn)).setText("Address : " + address.toUpperCase());
        ((TextView) findViewById(R.id.buscn)).setText("Bus : " + bus.toUpperCase());
        ((TextView) findViewById(R.id.typecn)).setText("CoachType : " + type);
        ((TextView) findViewById(R.id.dateview)).setText("JourneyDate : " + new RequestManager().processDate(date));
        StringTokenizer sst = new StringTokenizer(seatno, " ");
        int seat = sst.countTokens();
        String finalseats = "";
        int flag = 0;
        String s;
        for (int i = 0; i < seat; i++) {
            s = sst.nextToken();
            int k = Integer.parseInt(s);
            char t = (char) ((k / 4) + 65);
            k = k % 4;
            s = "";
            if (k == 0) {
                k = 4;
                t--;
            }
            s += t;
            s += Integer.toString(k);
            if (flag == 0) {
                finalseats += s;
                flag = 1;
            } else
                finalseats += ", " + s;
        }
        int price;
        if (bus.equals("GREENLINE"))
            price = 1000;
        if (type.contains("n"))
            price = 470;
        else price = 800;
        ((TextView) findViewById(R.id.billcn)).setText("Total Fare : " + Integer.toString(price) + " X " + Integer.toString(seat) + " = " + Integer.toString(seat * price) + " taka");

        ((TextView) findViewById(R.id.seatnocn)).setText("SeatNo : " + finalseats);
        s = time.replace('-',':');

                ((TextView) findViewById(R.id.timecn)).setText("Time : " + s);

        // taking screenshot
        Button save  = (Button) findViewById(R.id.buttonsavecn);
        save.setOnClickListener(this);
        Toast.makeText(ConfirmNotification.this,"tap camera icon to save your ticket",Toast.LENGTH_LONG).show();
    }

    private void takeScreenShot() {

        View v1 = L1.getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bm = v1.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

//you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "BusTicket"+new Date().toString() + ".jpg");

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//write the bytes in file
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

// remember close de FileOutput
        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(ConfirmNotification.this,"ticket successfully saved on storage",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_notification, menu);
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
    public void onClick(View v) {

        takeScreenShot();

    }
}
