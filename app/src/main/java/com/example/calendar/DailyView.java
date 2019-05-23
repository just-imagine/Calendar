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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.core.annotations.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class DailyView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent currentIntent;
    String checkedDate;
    Day thisDay;
    LinearLayout mainView;
    ArrayList<LinearLayout>slotCard;
    ScrollView Update;
    Dialog bookingDialog;
    String clickedTime;
    Dialog moveAppoint;
    ImageView monthTheme;
    ProgressDialog Loading;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainView=(LinearLayout)findViewById(R.id.mainView);

        currentIntent=getIntent();
        //retrieved checkeddate information
        checkedDate=currentIntent.getStringExtra("checkedDate");
        //hoder for cardview of slots
        slotCard=new ArrayList<>();
        //the day object which extends the month object
        thisDay=new Day(this);
        thisDay.setDate(thisDay.toDate(checkedDate));
        thisDay.setCheckedDate(checkedDate);
        //create the time slots for the day
        thisDay.expandSlots();

        //populates the view with textviews representing time slots
        addSlots(thisDay);
        Loading=new ProgressDialog(this);
        thisDay.setLoading(Loading);

        thisDay.DailySchedule();

        monthTheme=(ImageView)findViewById(R.id.dayDisplay);

        //make a scrollview which will update daily bookings
        Update=(ScrollView)findViewById(R.id.Update);

        Update.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                thisDay.DailySchedule();
                progressTrack(thisDay.completedSlots);
                expectedProgress();
            }
        });


        //set the dialog for making and cancelling bookings
        bookingDialog=new Dialog(this);
        bookingDialog.setContentView(R.layout.dialog_booking);

        thisDay.setBookingDialog(bookingDialog);

        //set title
        setTitle(thisDay.getMonth()+" "+thisDay.getYear());

        //set date on on circular icon
        TextView weekday=(TextView)findViewById(R.id.weekDay);
        weekday.setText(thisDay.getWeekDay().substring(0,3)+"\n"+thisDay.getDay());

        moveAppoint=new Dialog(this);
        moveAppoint.setContentView(R.layout.movebooking);

        progressTrack(thisDay.completedSlots);

        setTheme();
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
            Intent Progress=new Intent(getApplicationContext(),Statistics.class);
            Progress.putExtra("Statistics",thisDay.Stats());
            startActivity(Progress);
        } else if (id == R.id.nav_week) {
            Intent weekView=new Intent(getApplicationContext(),WeekView.class);
            weekView.putExtra("Month",thisDay.getMonth());
            weekView.putExtra("CurrentDate",thisDay.getCurrentDate());
            weekView.putExtra("CheckedDate",thisDay.getCheckedDate());
            weekView.putExtra("LongCurrentDate",""+new Date());
            startActivity(weekView);

        } else if (id == R.id.nav_month) {
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //update ui and add the time slots
    public void addSlots(Day d){
        ArrayList<LinearLayout>cardSlots=d.getSlotCards();
        for(int i=0;i<cardSlots.size();++i){
            LinearLayout c=cardSlots.get(i);
            mainView.addView(c);
        }

    }

/* get a relevent popup when a textview is clicked depending when its appointment or free */
    public void PopUp(View v){
        //info
        TextView Patient=(TextView)bookingDialog.findViewById(R.id.patient);
        TextView Patientemail=(TextView)bookingDialog.findViewById(R.id.email);

        Patient.setText("");
        Patientemail.setText("");

        TextView cancelSlot=(TextView)bookingDialog.findViewById(R.id.cancel);
        TextView checkout=(TextView)bookingDialog.findViewById(R.id.checkout) ;
        TextView moveBooking=(TextView)bookingDialog.findViewById(R.id.moveBooking);
        //the event detail textview should be given relevent information
        TextView timeDetails=(TextView)bookingDialog.findViewById(R.id.timedetails);
        clickedTime=((TextView) v).getHint().toString();

        cancelSlot.setVisibility(View.VISIBLE);

        checkout.setVisibility(View.VISIBLE);
        moveBooking.setVisibility(View.VISIBLE);

        int value=Integer.parseInt(clickedTime.substring(3,5))+15;
        String end=clickedTime.substring(0,3)+""+value;
        if(value==60){
            int l=Integer.parseInt(clickedTime.substring(0,2))+1;
            end=""+l+":00";
        }
        timeDetails.setText(thisDay.getWeekDay()+" , "+thisDay.getDay()+" "+thisDay.getMonth()+" "+thisDay.getYear()+"\n\nDuration "+clickedTime+"-"+end);


        if(((TextView) v).getText().toString().equals("Free")){
            cancelSlot.setVisibility(View.VISIBLE);
            cancelSlot.setText("Cancel");
            checkout.setVisibility(View.INVISIBLE);
            moveBooking.setVisibility(View.INVISIBLE);
            bookingDialog.show();
        }

        else if(((TextView) v).getText().toString().equals("Appointment")){
            checkout.setVisibility(View.VISIBLE);
            cancelSlot.setVisibility(View.VISIBLE);
            moveBooking.setVisibility(View.VISIBLE);
            //find the relevent booking with information from the day

            Booking b=thisDay.findBooking(clickedTime);
            Patient.setText(b.getName()+" "+b.getSurname());
            Patientemail.setText(b.getEmail());
            bookingDialog.show();
        }

        else if(((TextView) v).getText().toString().equals("Attended")){
            checkout.setVisibility(View.INVISIBLE);
            cancelSlot.setVisibility(View.INVISIBLE);
            moveBooking.setVisibility(View.INVISIBLE);
            Booking b=thisDay.findBooking(clickedTime);
            Patient.setText(b.getName()+" "+b.getSurname());
            Patientemail.setText(b.getEmail());
            bookingDialog.show();
        }
    }

    // when a slot is clicked we want to take action depending on what text it has
    public void Action(View v) {
        v = (TextView) v;
        if (((TextView) v).getText().toString().equals("Cancel")) {
            Booking b = thisDay.findBooking(clickedTime);
            if (b != null)
                thisDay.cancelBooking(b, mainView);

            else {

            }
        } else if (((TextView) v).getText().toString().equals("Checkout ")) {
            Booking b = thisDay.findBooking(clickedTime);
            if (b != null) {
                int a = 1;
                thisDay.checkoutBooking(b, mainView);
            }
        } else if (((TextView) v).getText().toString().equals("Move")) {

            moveAppoint.show();
        } else if (((Button) v).getText().toString().equals("move")) {
            Booking prior = thisDay.findBooking(clickedTime);
            EditText newDay = moveAppoint.findViewById(R.id.newday);
            EditText newTime = moveAppoint.findViewById(R.id.newtime);
            if (prior != null) {

                String moveToday = newDay.getText().toString();
                String line[] = moveToday.split("-");
                String day = "";
                String time = newTime.getText().toString();
                try{
                    day = line[0] + line[1] + line[2];
                    int a=1;
                    if(!time.equals("null") && time.length()==5 && time.contains(":")){
                    Booking after = new Booking(day, time, prior.getIdentity());
                    thisDay.moveSlot(prior, after, mainView, moveAppoint);}
                    else{
                        newTime.setError("The time format is HH:mm");
                    }

                    }
                    catch (IndexOutOfBoundsException e){
                    newDay.setError("The date format is YYYY-MM-dd");
                }

            }
        }

    }

    public void progressTrack(ArrayList<TextView>slots){

        ArrayList<LinearLayout>cards=thisDay.slotCards;


        for(int i=0;i<thisDay.timeSlots.size();++i){
            TextView s=thisDay.timeSlots.get(i);
            LinearLayout L=(LinearLayout)s.getParent();
            L.setBackgroundColor(Color.TRANSPARENT);
        }


      ///getst las attended appointment
      if(!slots.isEmpty()){
          if(slots.size()>=1){
          TextView last=slots.get(slots.size()-1);
          LinearLayout L=(LinearLayout)last.getParent();
          L.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borders));}
      }


      //move to any slot that should

    }

    public void expectedProgress(){
        ArrayList<LinearLayout>cards=thisDay.slotCards;
        ArrayList<Booking>bookings=thisDay.dailyBookings;

        }

    public void setTheme(){
        String Month=thisDay.getMonth();
        if(Month.equals("April")){
            monthTheme.setImageResource(R.drawable.april);
        }
        else if(Month.equals("March")){
            monthTheme.setImageResource(R.drawable.march);
        }
        else if(Month.equals("January")){
            monthTheme.setImageResource(R.drawable.january);
        }
        else if(Month.equals("February")){
            monthTheme.setImageResource(R.drawable.february);
        }
        else if(Month.equals("May")){
            monthTheme.setImageResource(R.drawable.may);
        }
        else if(Month.equals("June")){
            monthTheme.setImageResource(R.drawable.june);
        }
        else if(Month.equals("July")){
            monthTheme.setImageResource(R.drawable.july);
        }
        else if(Month.equals("August")){
            monthTheme.setImageResource(R.drawable.august);
        }
        else if(Month.equals("September")){
            monthTheme.setImageResource(R.drawable.september);}
        else if(Month.equals("October")){
            monthTheme.setImageResource(R.drawable.october);}
        else if(Month.equals("November")){
            monthTheme.setImageResource(R.drawable.november);}
        else{
            monthTheme.setImageResource(R.drawable.december);}
    }


 }
