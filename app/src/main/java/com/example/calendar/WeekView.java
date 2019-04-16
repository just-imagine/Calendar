package com.example.calendar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HorizontalScrollView TopHorizontal;
    HorizontalScrollView BottomHorizontal;
    TableRow TopWeekdays;
    TableRow BottomWeekdays;
    String CurrentMonth;
    String CurrenDate,CheckedDate;
    Intent currentIntent;
    MaterialCalendarView DropDownCalendar;
    TextView WeekDays[];

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        TopHorizontal=(HorizontalScrollView)findViewById(R.id.Tophorizontal);

        BottomHorizontal=(HorizontalScrollView)findViewById(R.id.horizontalScroll);
        TopWeekdays=(TableRow)findViewById(R.id.TopWeekdays);
        BottomWeekdays=(TableRow)findViewById(R.id.WeekDays);

        TopHorizontal.scrollTo(BottomHorizontal.getScrollX(),BottomHorizontal.getScrollY());
        BottomHorizontal.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
              TopHorizontal.scrollTo(scrollX,scrollY);
            }
        });


        TopHorizontal.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                BottomHorizontal.scrollTo(scrollX,scrollY);
            }
        });

        currentIntent=getIntent();
        CurrentMonth=currentIntent.getStringExtra("Month");
        CurrenDate=currentIntent.getStringExtra("CurrentDate");
        CheckedDate=currentIntent.getStringExtra("CheckedDate");
        //String =currentIntent.getStringExtra("LongCurrentDate");

        DropDownCalendar=(MaterialCalendarView)findViewById(R.id.DropDownCalendar);
        String formateddate=CheckedDate.substring(0,4)+"/"+CheckedDate.substring(4,6)+"/"+CheckedDate.substring(6,8);

        DropDownCalendar.setDateSelected(Calendar.getInstance().getTime(), true);
        DropDownCalendar.setCurrentDate(ToDate(CheckedDate));
        DropDownCalendar.setDateSelected(ToDate(formateddate), true);
        DropDownCalendar.setCurrentDate(Calendar.getInstance());
        DropDownCalendar.setSelectionColor(Color.RED);
        DropDownCalendar.addDecorator(new CurrentDateDecorator(this));

        WeekDays=Weekdays();


        setDays();

        setTitle(getMonth(CurrentMonth));
        toolbar.setTitleTextColor(Color.BLACK);

        Weekdays();

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
        getMenuInflater().inflate(R.menu.week_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.item1){

            ShowCalendar();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void  PopulateView(){


    }

    public  String[] Time() {
        int value = 15;
        int hour = 8;
        String Times[]=new String[40];
        String Time="";
        String properTime="";
        for (int i = 0; i < 40; ++i) {

            if (value == 60) {
                value = 0;
                ++hour;
            }
            properTime = "" + (hour) + ":" + value;
            Time = "";
            if (hour < 10) {
                if (value != 0) {
                    Time = "0" + properTime;
                } else {
                    Time = "0" + properTime + "0";
                }
            } else {
                if (value != 0) {
                    Time = "" + properTime;
                } else {
                    Time = "" + properTime + "0";
                }
            }
            String hint = Time;
            if (hour < 12) {
                Time = Time + " AM";
            } else {
                Time = Time + " PM";
            }
            value=value+15;
            Times[i]=Time;
        }
        return Times;

    }

    public  void ShowCalendar(){



        TextView ChangeCalendar=(TextView)findViewById(R.id.ChangeCalendar);
        if(DropDownCalendar.getLayoutParams().height==0){
            DropDownCalendar.setLayoutParams(TopWeekdays.getLayoutParams());
        }

        else{
            DropDownCalendar.setLayoutParams(ChangeCalendar.getLayoutParams());
        }

    }

    public  String getMonth(String x){

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
            return "June";
        }

        else if(x.equals("Jul")){
            return  "Jul";

        }

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

    public TextView[] Weekdays(){


        Resources r = getResources();
        String name = getPackageName();
        String WeekDays[]={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        TextView Weekdays[]=new TextView[7];
        DropDownCalendar.setCurrentDate(Calendar.getInstance());
        for(int i=0;i<WeekDays.length;++i) {
            TextView weekday;
            weekday = (TextView) findViewById(r.getIdentifier(WeekDays[i], "id", name));
            Weekdays[i]=weekday;
          //  Weekdays[i].setTextSize(18);

        }

        return  Weekdays;
    }

    public Date ToDate(String line){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d = sdf.parse(line);
            return  d;
        } catch (ParseException ex) {

            Log.v("Exception", ex.getLocalizedMessage());
            return  null;
        }


    }

    public  int Offset(String line){
        if(line.equals("Mon")){
            return  0;
        }

        else if(line.equals("Tue")){
            return 1;
        }

        else if(line.equals("Wed")){
            return  2;
        }

        else if(line.equals("Thu")){
            return  3;
        }

        else if(line.equals("Fri")){
            return  4;
        }

        else if(line.equals("Sat")){
            return  5;
        }

        else{
            return  6;
        }
    }

    public void setDays(){
        CalendarDay Day=DropDownCalendar.getSelectedDate();
        //Toast.makeText(getApplicationContext(),""+Day.getDate(),Toast.LENGTH_LONG).show();


        Date selectedDate=Day.getDate();
        String LongDate=""+selectedDate;
        String DayOfWeek=LongDate.substring(0,3);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(selectedDate);

        int startNumber=Integer.parseInt(CheckedDate.substring(6,8).trim())-Offset(DayOfWeek);
        int max=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Toast.makeText(getApplicationContext(),""+DayOfWeek +" "+Offset(DayOfWeek),Toast.LENGTH_LONG).show();

        for(int i=0;i<WeekDays.length;++i){
            if(startNumber<=max)
            WeekDays[i].append("\n"+startNumber);

            else{
                startNumber=1;
                WeekDays[i].append("\n"+startNumber);
            }

            ++startNumber;
        }


    }




}
