package com.example.calendar;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.cert.TrustAnchor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CalendarView calendar;
    Date currentDate;
    String formattedDate;
    TextView []timeSlots;
    ArrayList<TextView>clickabletimeSlots;
    ArrayList<Booking>Bookings;
    String highlightDate;
    Dialog diagBooked;
    Dialog diagBlocked;
    Dialog diagUnblocked;
    Button unBlockSlot,BlockSlot;
    ScrollView Schedule;
    String globalTime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Bookings=new ArrayList<>();
        //initialize timeslots and calendar
        calendar=(CalendarView)findViewById(R.id.calendar);
        timeSlots=new TextView[11];
        Schedule=(ScrollView)findViewById(R.id.ScrollView);
        //get todays date which will be used in database queries
        currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        formattedDate = df.format(currentDate);
        highlightDate=formattedDate;

        //initielize the timeSlots and assignthem
        InitializeSlots();
        AssigntimeSlots(formattedDate);

        clickabletimeSlots=new ArrayList<>(Arrays.asList(timeSlots));

        //set on date change for calendar view
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub
                String information="";
                String day="";
                String Month="";
                month=month+1;
                if(dayOfMonth<10){
                    day="0"+dayOfMonth;
                }

                else {
                    day=day+dayOfMonth;
                }

                if(month<10){
                    Month="0"+month;
                }

                else{
                    Month=""+month;
                }

                information=information+year+Month+day;
                highlightDate=information;
                AssigntimeSlots(information);

            }
        });

        Schedule.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                AssigntimeSlotsOnTouch(highlightDate);
            }
        });

        diagBooked = new Dialog(this);
        diagBooked.setContentView(R.layout.dialog_viewbooked);
        diagBlocked=new Dialog(this);
        diagBlocked.setContentView(R.layout.dialog_blocked);
        diagUnblocked=new Dialog(this);
        diagUnblocked.setContentView(R.layout.dialog_unblock);
        unBlockSlot=(Button)diagBlocked.findViewById(R.id.unblockslot);
        unBlockSlot.setOnClickListener(this);
        BlockSlot=(Button)diagUnblocked.findViewById(R.id.blockslot);
        BlockSlot.setOnClickListener(this);

    }

    //assign id to all timeSlots textviews and give them transparent text which is their time
    public  void InitializeSlots(){

        Resources r = getResources();
        String name = getPackageName();
        int t=8;
        for(int i=1;i<=11;++i) {
            timeSlots[i - 1] = (TextView) findViewById(r.getIdentifier("t" + i, "id", name));
            timeSlots[i-1].setTextColor(Color.TRANSPARENT);
            timeSlots[i-1].setOnClickListener(this);
            if(t<10){
            timeSlots[i-1].setText("0"+t+":00");}

            else{
                timeSlots[i-1].setText(t+":00");
             }


             ++t;
        }

    }


    public void AssigntimeSlots(String date){
        //as i assign i dont want the user to use any background information
        int t=8;
        for(int i=0;i<timeSlots.length;++i){
            timeSlots[i].setBackgroundColor(Color.TRANSPARENT);
            timeSlots[i].setTextColor(Color.TRANSPARENT);
            if(t<10){
                timeSlots[i].setText("0"+t+":00");}

            else{
                timeSlots[i].setText(t+":00");
            }

            ++t;
        }
        //put the current date and go get todays time slots
        ContentValues params=new ContentValues();
        params.put("date",date);

        //GET FROM GENERATE BOOKINGS THE SL
        if(Integer.parseInt(highlightDate)-Integer.parseInt(formattedDate)>=0) {
            AsyncHTTPPost getSlots = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/Activities.php", params) {
                @Override
                protected void onPostExecute(String output) {
                    ArrayList<String> databasetimes = new ArrayList<>();
                    ArrayList<Booking> Slots = new ArrayList<>();
                    try {
                        JSONArray results = new JSONArray(output);
                        for (int i = 0; i < results.length(); ++i) {
                            JSONObject obj = results.getJSONObject(i);
                            String Date = obj.getString("DATE");
                            String Time = obj.getString("TIME").substring(0, 5);
                            String Patient = obj.getString("ID_NUMBER");
                            int Validity = obj.getInt("VALIDITY");

                            Booking temp = new Booking(Date, Time, Patient, Validity);
                            Slots.add(temp);
                        }

                        Bookings=Slots;

                        for (int i = 0; i < clickabletimeSlots.size(); ++i) {
                            String line = clickabletimeSlots.get(i).getText().toString();
                            for (int j = 0; j < Slots.size(); ++j) {
                                Booking temp = Slots.get(j);
                                if (temp.getTime().equals(line)) {
                                    if (temp.Blocked()) {
                                        clickabletimeSlots.get(i).setBackgroundColor(Color.parseColor("#D81B60"));
                                        clickabletimeSlots.get(i).setTextColor(Color.WHITE);
                                        clickabletimeSlots.get(i).setText("Blocked");

                                    } else if (temp.Free()) {
                                        clickabletimeSlots.get(i).setBackgroundColor(Color.parseColor("#16a085"));
                                        clickabletimeSlots.get(i).setTextColor(Color.WHITE);
                                        clickabletimeSlots.get(i).setText("Free");
                                    } else if (temp.Booked()) {
                                        clickabletimeSlots.get(i).setBackgroundColor(Color.parseColor("#FF8300"));
                                        clickabletimeSlots.get(i).setTextColor(Color.WHITE);
                                        clickabletimeSlots.get(i).setText("Appointment");
                                    }

                                }

                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            getSlots.execute();

        }

        else{
            for (int i = 0; i < timeSlots.length; ++i) {
                timeSlots[i].setTextColor(Color.BLACK);
                timeSlots[i].setText("-----------");
            }

        }
    }

    public void AssigntimeSlotsOnTouch(String date){
        //as i assign i dont want the user to use any background information
        int t=8;

        for(int i=0;i<timeSlots.length;++i){
            timeSlots[i].setHintTextColor(Color.TRANSPARENT);
            if(t<10){

                timeSlots[i].setHint("0"+t+":00");}

            else{
                timeSlots[i].setHint(t+":00");
            }

            ++t;
        }
        //put the current date and go get todays time slots
        ContentValues params=new ContentValues();
        params.put("date",date);

        //GET FROM  BOOKINGS THE SLOTS (FREE,BLOCKED AND BOOKED)
        if(Integer.parseInt(highlightDate)-Integer.parseInt(formattedDate)>=0){
        AsyncHTTPPost getSlots=new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/Activities.php",params) {
            @Override
            protected void onPostExecute(String output) {
                ArrayList<String>databasetimes=new ArrayList<>();
                ArrayList<Booking>Slots=new ArrayList<>();
                try {
                    JSONArray results=new JSONArray(output);
                    for(int i=0;i<results.length();++i){
                        JSONObject obj=results.getJSONObject(i);
                        String Date=obj.getString("DATE");
                        String Time=obj.getString("TIME").substring(0,5);
                        String Patient=obj.getString("ID_NUMBER");
                        int Validity=obj.getInt("VALIDITY");

                        Booking temp=new Booking(Date,Time,Patient,Validity);
                        Slots.add(temp);
                    }

                    for(int i=0;i<clickabletimeSlots.size();++i){
                        String line=clickabletimeSlots.get(i).getHint().toString();
                        for(int j=0;j<Slots.size();++j){
                            Booking temp=Slots.get(j);
                            if(temp.getTime().equals(line)){
                                if(temp.Blocked()){
                                    clickabletimeSlots.get(i).setBackgroundColor(Color.parseColor("#D81B60"));
                                    clickabletimeSlots.get(i).setTextColor(Color.WHITE);
                                    clickabletimeSlots.get(i).setText("Blocked");

                                }

                                else if(temp.Free()){
                                    clickabletimeSlots.get(i).setBackgroundColor(Color.parseColor("#16a085"));
                                    clickabletimeSlots.get(i).setTextColor(Color.WHITE);
                                    clickabletimeSlots.get(i).setText("Free");
                                }

                                else if(temp.Booked()){
                                    clickabletimeSlots.get(i).setBackgroundColor(Color.parseColor("#FF8300"));
                                    clickabletimeSlots.get(i).setTextColor(Color.WHITE);
                                    clickabletimeSlots.get(i).setText("Appointment");
                                }

                            }

                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getSlots.execute();}

        else{
            for(int i=0;i<timeSlots.length;++i){
                timeSlots[i].setTextColor(Color.BLACK);
                timeSlots[i].setText("-----------");
            }
        }

    }

    @Override
    public void onClick(View v) {

        if(clickabletimeSlots.contains(v)){
            Resources r = getResources();
            String name = getPackageName();
            String time="",databasetime="";
            if(v.getId()==r.getIdentifier("t" + 1, "id", name)){
                time="08:00";
                databasetime="080000";


            }

            else if(v.getId()==r.getIdentifier("t" + 2, "id", name)){
                time="09:00";
                databasetime="090000";
            }

            else if(v.getId()==r.getIdentifier("t" + 3, "id", name)){
                time="10:00";
                databasetime="100000";
            }


            else if(v.getId()==r.getIdentifier("t" + 4, "id", name)){
                time="11:00";
                databasetime="110000";
            }


            else if(v.getId()==r.getIdentifier("t" + 5, "id", name)){
                time="12:00";
                databasetime="120000";
            }


            else if(v.getId()==r.getIdentifier("t" + 6, "id", name)){
                time="13:00";
                databasetime="130000";
            }

            else if(v.getId()==r.getIdentifier("t" + 7, "id", name)){
                time="14:00";
                databasetime="140000";
            }

            else if(v.getId()==r.getIdentifier("t" + 8, "id", name)){
                time="15:00";
                databasetime="150000";
            }

            else if(v.getId()==r.getIdentifier("t" + 9, "id", name)){
                time="16:00";
                databasetime="160000";
            }

            else if(v.getId()==r.getIdentifier("t" + 10, "id", name)){
                time="17:00";
                databasetime="170000";
            }


            else if(v.getId()==r.getIdentifier("t" + 11, "id", name)){
                time="18:00";
                databasetime="180000";
            }

            //now that we have the clicked textview if its highlighted we should be able
            //to see details of appointment and option to cancel
            globalTime=databasetime;
            TextView clicked=(TextView)v;
            ContentValues params=new ContentValues();
            params.put("date",highlightDate);
            params.put("time",databasetime);
            String instruction=clicked.getText().toString();

            if(instruction.equals("Blocked")){
                diagBlocked.show();
            }

            else if(instruction.equals("Free")){
                diagUnblocked.show();
            }

            else if(instruction.equals("Appointment")){
                Booking temp=new Booking("","","",-1);
                for(int i=0;i<Bookings.size();++i){
                    if(Bookings.get(i).getTime().equals(time)){
                        temp=Bookings.get(i);
                    }
                }

                String identity=temp.getPatient();
                if(!identity.equals("")){
                    ContentValues info=new ContentValues();
                    info.put("id",identity);
                    AsyncHTTPPost details=new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/details.php",info) {
                        @Override
                        protected void onPostExecute(String output) {
                            try {
                                JSONArray result =new JSONArray(output);
                                String name="",surname="",contact="",email="";
                                for(int i=0;i<result.length();++i){
                                    JSONObject obj=result.getJSONObject(i);
                                     name="\nNAME : "+obj.getString("NAME");
                                     surname="SURNAME :"+obj.getString("SURNAME");
                                     contact="CONTACT : 0"+obj.getString("CONTACT_NO");
                                     email="EMAIL : "+obj.getString("EMAIL_ADDRESS");
                                }
                                String infoformat=name+"\n\n"+surname+"\n\n"+contact+"\n\n"+email;
                                TextView Pinformation=(TextView)diagBooked.findViewById(R.id.details);
                                Pinformation.setTextColor(Color.BLACK);
                                Pinformation.setText(infoformat);
                                diagBooked.show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    details.execute();
                }




            }




        }


        else if(v.equals(unBlockSlot)){
            String instruction=unBlockSlot.getText().toString();
            ContentValues Params=new ContentValues();
            Params.put("state",1);
            Params.put("date",highlightDate);
            Params.put("time",globalTime);
            if(instruction.equals("Unblock Slot")){
                AsyncHTTPPost unblock=new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/Block.php",Params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if(output.equals("success")){
                            AssigntimeSlots(highlightDate);
                            diagBlocked.dismiss();
                        }

                    }
                };
                unblock.execute();
            }


        }

        else if(v.equals(BlockSlot)){
            String instruction=BlockSlot.getText().toString();
            ContentValues Params=new ContentValues();
            Params.put("state",0);
            Params.put("date",highlightDate);
            Params.put("time",globalTime);
            if(instruction.equals("block Slot")){
                AsyncHTTPPost block=new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/Block.php",Params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if(output.equals("success")){
                            // BlockSlot.setText("BLOCK");
                            AssigntimeSlots(highlightDate);
                            diagUnblocked.dismiss();
                        }

                    }
                };
                block.execute();
            }

        }

    }
}
