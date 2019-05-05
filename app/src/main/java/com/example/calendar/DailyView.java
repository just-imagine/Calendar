package com.example.calendar;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class DailyView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //cglobal variables which we need access to
    Intent m;
    LinearLayout Items;
    TextView time;
    TextView Slot;
    View Divider;
    FrameLayout vert;
    TextView WeekDay;
    ImageView display;
    String current_date,checked_date;
    ArrayList<TextView>TimeSlots;
    ArrayList<Booking>Bookings;
    Toolbar Heading;
    Dialog booking;
    String DayOfWeek,DayOfMonth,MonthOfYear;
    TextView Checkout;
    TextView Cancel;
    ScrollView UpdateData;
    String clickedtime;
    ProgressDialog dialog;
    CardView lastcarddone,FirstCard;
    LinearLayout Master;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Master=(LinearLayout)findViewById(R.id.master);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //change the colour of the heading
        Heading=(Toolbar)findViewById(R.id.toolbar);
        Heading.setTitleTextColor(Color.BLACK);
        //assign variables Items is the linear layout containing everything
        Items=(LinearLayout)findViewById(R.id.col);
        time=(TextView)findViewById(R.id.slotOne);
        Slot=(TextView)findViewById(R.id.g);
        Divider=(View)findViewById(R.id.divider);
        vert=(FrameLayout)findViewById(R.id.verticaldiv);
        WeekDay=(TextView)findViewById(R.id.weekDay);
        display=(ImageView)findViewById(R.id.dayDisplay);

        //initialize array of bookings for the day
        Bookings=new ArrayList<>();
        //retrieve current intent to get the passed information from previous intent
        m=getIntent();
        DayOfWeek=m.getStringExtra("WeekDay");
        DayOfMonth=m.getStringExtra("Date");
        String Information=DayOfWeek+"\n"+DayOfMonth;
        String Picture=m.getStringExtra("Month");
        current_date=m.getStringExtra("Current");
        checked_date=m.getStringExtra("Checked");

        lastcarddone=(CardView)findViewById(R.id.sample);
        FirstCard=(CardView)findViewById(R.id.sample);
        WeekDay.setText(Information);

        Display(Picture);

        MonthOfYear=Month(Picture);
        setTitle(MonthOfYear+" "+checked_date.substring(0,4));

        //assign timeslots array
        TimeSlots=new ArrayList<>();

        //fill in all timeslots by duplicating first one
        PopulateView();

        //generate schedule for the day
        DailySchedule(checked_date);

        //these views are for the popup when a slot is free or marked as appointment

        booking=new Dialog(this);
        booking.setContentView(R.layout.dialog_booking);
        Checkout=(TextView)booking.findViewById(R.id.checkout);
        Cancel=(TextView)booking.findViewById(R.id.cancel);

        //everytime you scroll we want to check whether data from database has changed or not and display rel time data
        UpdateData=(ScrollView)findViewById(R.id.touchslots);
        UpdateData.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                DailySchedule(checked_date);
            }
        });

        toolbar.setTitleTextColor(Color.BLACK);
        //dialog used for loading
        dialog = new ProgressDialog(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_progress) {
            Intent Progress=new Intent(getApplicationContext(),Progress.class);
            ArrayList<Integer>Statistics=PieComputation();
            Progress.putIntegerArrayListExtra("Statistics",Statistics);
            startActivity(Progress);
        } else if (id == R.id.nav_week) {
            Intent weekView=new Intent(getApplicationContext(),WeekView.class);
            weekView.putExtra("Month",MonthOfYear);
            weekView.putExtra("CurrentDate",current_date);
            weekView.putExtra("CheckedDate",checked_date);
            weekView.putExtra("LongCurrentDate",""+ Calendar.getInstance().getTime());
            startActivity(weekView);

        } else if (id == R.id.nav_month) {
            finish();

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /* sets relevent picture depending on month*/
    public void  Display(String input){
        if(!input.equals("")){
            String Month=input;
            if(Month.equals("Apr")){
                display.setImageResource(R.drawable.april);
            }
            else if(Month.equals("Mar")){
                display.setImageResource(R.drawable.march);
            }
            else if(Month.equals("Jan")){
                display.setImageResource(R.drawable.january);
            }
            else if(Month.equals("Feb")){
                display.setImageResource(R.drawable.february);
            }
            else if(Month.equals("May")){
                display.setImageResource(R.drawable.may);
            }
            else if(Month.equals("Jun")){
                display.setImageResource(R.drawable.june);
            }
            else if(Month.equals("Jul")){
                display.setImageResource(R.drawable.july);
            }
            else if(Month.equals("Aug")){
                display.setImageResource(R.drawable.august);
            }
            else if(Month.equals("Sep")){
                display.setImageResource(R.drawable.september);}
            else if(Month.equals("Oct")){
                display.setImageResource(R.drawable.october);}
            else if(Month.equals("Nov")){
                display.setImageResource(R.drawable.november);}

            else{
                display.setImageResource(R.drawable.december);}
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    /*what this method does is it populates the day view layout with textviews representing time slots
         the layout starts out with one textview that has the first slot for the day and then duplicates it to
        produce all other timeslots */
    public void PopulateView(){
        int value=15;
        int hour=8;
        int current_val= Integer.parseInt(current_date);
        int checked_val=Integer.parseInt(checked_date);
        Slot.setText("");
        Slot.setTextSize(18);
        Slot.setBackgroundColor(Color.parseColor("#008577"));
        String properTime;
        for(int i=0;i<40;++i){
            if(value==60){
                value=0;
                ++hour;
            }
            properTime=""+(hour)+":"+value;
            String Time="";
            if(hour<10){
                if(value!=0){
                    Time="0"+properTime;}
                else{
                    Time="0"+properTime+"0";
                }
            }
            else{
                if(value!=0){
                    Time=""+properTime;}

                else{
                    Time=""+properTime+"0";
                }
            }
            String hint=Time;
            if(hour<12){
                Time=Time+" AM";
            }
            else{
                Time=Time+" PM";
            }
            //have one version of cardview with textview then duplicate its layout
            CardView cardView=new CardView(this);
            cardView.setId(i+100);
            LinearLayout temp=new LinearLayout(this);
            temp.setLayoutParams(Items.getLayoutParams());
            TextView a=new TextView(this);
            a.setLayoutParams(time.getLayoutParams());
            a.setTextSize(15);
            a.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            a.setTextColor(Color.BLACK);
            a.setText(Time);
            TextView b=new TextView(this);
            b.setId(i);
            b.setLayoutParams(Slot.getLayoutParams());
            b.setTextSize(18);
            b.setHint(hint);
            b.setHintTextColor(Color.TRANSPARENT);
            b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            b.setPadding(12,12,12,12);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.parseColor("#008577"));
            b.setText("Free");
            Slot.setText("Free");
            if(current_val-checked_val>0){
                b.setBackgroundColor(Color.parseColor("#d13c04"));
                Slot.setBackgroundColor(Color.parseColor("#d13c04"));
                b.setText("--------------");
                Slot.setText("-----------");
            }
            //note setting hint is used as way of keeping track of which textview belongs to which time
            View c=new View(this);
            c.setLayoutParams(Divider.getLayoutParams());
            c.setBackgroundColor(Color.LTGRAY);

            FrameLayout d=new FrameLayout(this);
            d.setBackgroundColor(Color.LTGRAY);
            d.setLayoutParams(vert.getLayoutParams());

            temp.addView(a);
            temp.addView(d);
            temp.addView(b);
            cardView.addView(c);
            cardView.addView(temp);
            Items.addView(cardView);
            value=value+15;
            //set onclicklistener for all the textviews
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp(v);
                }
            });
            TimeSlots.add(b);
        }

        Slot.setHintTextColor(Color.TRANSPARENT);
        Slot.setTextColor(Color.WHITE);
        Slot.setHint("08:00");
        Slot.setPadding(12,12,12,12);
        TimeSlots.add(Slot);
        CardView cardView=new CardView(this);
        View c=new View(this);
        c.setLayoutParams(Divider.getLayoutParams());
        c.setBackgroundColor(Color.LTGRAY);
        TextView space=new TextView(this);
        space.setTextSize(10);
        cardView.addView(c);
        cardView.addView(space);
        Items.addView(cardView);
    }

    //generates full name of month given a substring of its name
    public  String Month(String x){
        if(x.equals("Apr")){
           return  "April";
        }
        else if(x.equals("Mar")){
            return  "March";
        }
        else if(x.equals("Jan")){
            return  "January";
        }
        else if(x.equals("Feb")){
            return  "February";
        }
        else if(x.equals("May")){
            return  "May";
        }
        else if(x.equals("Jun")){
            return  "June";
        }
        else if(x.equals("Jul")){
            return  "July";}
        else if(x.equals("Aug")){
            return  "August";
        }
        else if(x.equals("Sep")){
            return  "September";}

        else if(x.equals("Oct")){
            return  "October";}

        else if(x.equals("Nov")){
            return  "November";}
        else{
            return  "December";}
        }
    /* queries the database for a schedule of the selected day */
    public void DailySchedule(String date){
        //clear the array so that new data can be added

        Bookings=new ArrayList<>();

        ContentValues Params=new ContentValues();
        int current_value=Integer.parseInt(current_date);
        int checked_value=Integer.parseInt(checked_date);
        Params.put("DATE",date);
        //currently works when a date is greater than or equals
        if(checked_value-current_value>=0){
            AsyncHTTPPost Schedule = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/CSearches.php", Params) {
                @Override
                protected void onPostExecute(String output) {
                    Bookings=new ArrayList<>();
                    try {
                        JSONArray results = new JSONArray(output);
                        for (int i = 0; i < results.length(); ++i) {
                            JSONObject obj = results.getJSONObject(i);
                            String Name = obj.getString("NAME");
                            String Surname = obj.getString("SURNAME");
                            String Identity = obj.getString("ID_NUMBER");
                            String Email = obj.getString("EMAIL_ADDRESS");
                            String Contact = obj.getString("CONTACT_NO");
                            String Date = obj.getString("DATE");
                            String Time = obj.getString("TIME").substring(0, 5);
                            String CheckoutTime=obj.getString("CHECKOUT_TIME").substring(0, 5);
                            int State = obj.getInt("STATE");
                            Booking temp = new Booking(Name, Surname, Identity, Contact, Email, Date, Time,CheckoutTime, State);
                            Bookings.add(temp);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // in case data has been deleted
                    for(int i=0;i<TimeSlots.size();++i){
                        TextView s=TimeSlots.get(i);
                        s.setBackgroundColor(Color.parseColor("#008577"));
                        s.setText("Free");
                        s.setTextColor(Color.WHITE);
                    }

                    Booking other=LastAttended();

                    for (int j = 0; j < Bookings.size(); ++j) {
                        Bookings.get(j).OccupySlots(TimeSlots,other);
                    }


                    MoveTracker(other);


                }
            };

            Schedule.execute();}
        else{
            for(int i=0;i<TimeSlots.size();++i){
                TextView s=TimeSlots.get(i);
                s.setBackgroundColor(Color.parseColor("#d13c04"));
                s.setText("unavailable");
                s.setTextColor(Color.WHITE);
            }

        }

    }

/* get a relevent popup when a textview is clicked depending when its appointment or free */
    public void PopUp(View V){
        TextView timeDetails=(TextView)booking.findViewById(R.id.timedetails);
        TextView Patient=(TextView)booking.findViewById(R.id.patient);
        TextView Patientemail=(TextView)booking.findViewById(R.id.email);
        String time=((TextView) V).getHint().toString();
        clickedtime=((TextView) V).getHint().toString();

        int value=Integer.parseInt(time.substring(3,5))+15;
        int duration=Integer.parseInt(time.substring(3,5))+value;

        if(duration==105){
            int hour=Integer.parseInt(time.substring(0,2));
            hour=hour+1;
            timeDetails.setText(DayOfWeek+" , "+DayOfMonth+" "+MonthOfYear+"\n\nDuration "+time+"-0"+hour+":00");
        }
        else{
            timeDetails.setText(DayOfWeek+" , "+DayOfMonth+" "+MonthOfYear+"\n\nDuration "+time+"-"+time.substring(0,3)+""+value);
        }

        if(((TextView) V).getText().toString().equals("Appointment")){
            Booking viewing=FindBooking(time);
            Checkout.setText("Checkout");
            Cancel.setText("Cancel");
            Checkout.setBackgroundColor(Color.TRANSPARENT);
            if(viewing!=null){
                String Name=viewing.getName();
                String Surname=viewing.getSurname();
                String Email=viewing.getEmail();
                Patient.setText(Name+"  "+Surname);
                Patientemail.setText(Email);
                Checkout.setVisibility(View.VISIBLE);
                Cancel.setVisibility(View.VISIBLE);
                booking.show();
            }
        }

        else if(((TextView) V).getText().toString().equals("Attended")){
            Booking viewing=FindBooking(time);
            if(viewing!=null){
                String Name=viewing.getName();
                String Surname=viewing.getSurname();
                String Email=viewing.getEmail();
                Patient.setText(Name+"  "+Surname);
                Patientemail.setText(Email);
                Checkout.setVisibility(View.VISIBLE);
                Checkout.setText("  Attended ");
                Checkout.setBackgroundColor(Color.parseColor("#003366"));
                Cancel.setVisibility(View.INVISIBLE);
                booking.show();
            }
        }

        else if(((TextView) V).getText().toString().equals("Free")){
            Patientemail.setText("");
            Patient.setText("");
            Checkout.setVisibility(View.INVISIBLE);
            Cancel.setVisibility(View.VISIBLE);
            Cancel.setText("Block");
            booking.show();
        }

        else if(((TextView) V).getText().toString().equals("Blocked")){
            timeDetails.setVisibility(View.INVISIBLE);
            Patient.setVisibility(View.INVISIBLE);
            Patientemail.setVisibility(View.INVISIBLE);
            Checkout.setVisibility(View.INVISIBLE);
            Cancel.setText("Free");
            booking.show();
        }
    }

    // when a slot is clicked we want to take action depending on what text it has
    public void Action(View v){
        //we want to do stuff depending on whether there was appoinment or slot is free or blocked
        v=(TextView)v;
        if(((TextView) v).getText().toString().equals("Checkout"))
            if(current_date.equals(checked_date)){
                ContentValues Params=new ContentValues();
                Params.put("DATE",current_date);
                Params.put("TIME",clickedtime);

                AsyncHTTPPost chekoutAppointment=new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/Check.php",Params) {
                    @Override
                    protected void onPostExecute(String output) {
                        if(output.equals("success")){
                            booking.dismiss();
                            DailySchedule(current_date);
                            dialog.dismiss();
                        }

                        else{
                            booking.dismiss();
                            DailySchedule(current_date);
                            dialog.dismiss();
                        }


                    }



                };
                chekoutAppointment.execute();
                dialog = ProgressDialog.show(DailyView.this, "",
                        "Loading. Please wait...", true);
            }

            else{
                dialog = ProgressDialog.show(DailyView.this, "",
                        "Loading. Please wait...", true);

                Snackbar.make(Master, "Cannot checkout a future  event", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                booking.dismiss();
                dialog.dismiss();
            }
    }

    //gets a booking in the day by mapping it using time
    public Booking FindBooking(String time){
        for(int i=0;i<Bookings.size();++i){
            Booking t=Bookings.get(i);
            if(t.getTime().equals(time)){
                return  t;
            }
        }
        return  null;
    }

    public Booking LastAttended() {
        Booking LastAttended = null;
        for (int i = 0; i < Bookings.size(); ++i) {
            Booking t = Bookings.get(i);
            if (t.Completed()) {
                LastAttended=t;
            }

        }
        return  LastAttended;
    }

    public void MoveTracker(Booking other){
        //any slot not the first one
       if(lastcarddone!=null && other!=null && !other.getTime().equals("08:00")){
           lastcarddone.setElevation(0);
           lastcarddone.setBackgroundColor(Color.TRANSPARENT);
           FirstCard.setElevation(0);
           FirstCard.setBackgroundColor(Color.TRANSPARENT);

           lastcarddone=(CardView)findViewById(other.getCardidentifier()+100);
           if(lastcarddone!=null){
               lastcarddone.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borders));
               lastcarddone.setElevation(20);
           }
       }

       else if(other!=null && other.getTime().equals("08:00")){
           FirstCard.setElevation(20);
           FirstCard.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borders));
          // lastcarddone=FirstCard;
       }

    }

    public  ArrayList<Integer> PieComputation(){
        int AttendedBookings=0;
        int PendingBookings=0;
        int ScheduleTime=0;
        int first=0;
        for(int i=0;i<Bookings.size();++i){
            Booking B=Bookings.get(i);
            if(B.Completed()){
                ++AttendedBookings;
                if(first==0) {

                    ScheduleTime += B.getDelay();
                    first=ScheduleTime;
                }

                else{
                    ScheduleTime += B.getDelay()-first;
                    int  x=1;
                }
            }

            else if(B.Booked()){
                ++PendingBookings;
            }
        }

        if(ScheduleTime>2){
            ScheduleTime=2;
        }

        else{
            ScheduleTime=1;
        }

        ArrayList<Integer>Statistics=new ArrayList<>();
        Collections.addAll(Statistics,AttendedBookings,PendingBookings,ScheduleTime);

        return  Statistics;
    }
    //still to implement
    public  void BlockSlot(){
    }
    //still to implement
    public void CancelSlot(){
    }



    }
