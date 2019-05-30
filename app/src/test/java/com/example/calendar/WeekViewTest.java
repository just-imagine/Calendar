package com.example.calendar;

import android.content.Intent;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(RobolectricTestRunner.class)
public class WeekViewTest {


    WeekView myHome;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, HomeScreen.class);
        String temp="20190527";
        intent.putExtra("Month", "June");
        intent.putExtra("CurrentDate", temp);
        intent.putExtra("CheckedDate", temp);
        myHome = Robolectric.buildActivity(WeekView.class, intent).setup().get();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void check() {
        assertNotNull(myHome);
        myHome.BottomHorizontal.scrollTo(2,3);

    }
    @Test
    public void getMonth(){
        final WeekView temp=new WeekView();
        final String results=temp.getMonth("Sep");
        assertEquals("September",results);
        final String results1=temp.getMonth("Jan");
        assertEquals("January",results1);
        final String results2=temp.getMonth("Feb");
        assertEquals("February",results2);
        final String results3=temp.getMonth("Mar");
        assertEquals("March",results3);
        final String results4=temp.getMonth("Apr");
        assertEquals("April",results4);
        final String results5=temp.getMonth("May");
        assertEquals("May",results5);
        final String results6=temp.getMonth("Jun");
        assertEquals("June",results6);
        final String results7=temp.getMonth("Jul");
        assertEquals("July",results7);
        final String results8=temp.getMonth("Aug");
        assertEquals("August",results8);
        final String results10=temp.getMonth("Oct");
        assertEquals("October",results10);
        final String results11=temp.getMonth("Nov");
        assertEquals("November",results11);
        final String results12=temp.getMonth("Dec");
        assertEquals("December",results12);
    }
    @Test
    public void Offset(){
        final WeekView temp=new WeekView();
        final int results=temp.Offset("Mon");
        assertEquals(0,results);
        final int results1=temp.Offset("Tue");
        assertEquals(1,results1);
        final int results2=temp.Offset("Wed");
        assertEquals(2,results2);
        final int results3=temp.Offset("Thu");
        assertEquals(3,results3);
        final int results4=temp.Offset("Fri");
        assertEquals(4,results4);
        final int results5=temp.Offset("Sat");
        assertEquals(5,results5);
        final int results6=temp.Offset("Sun");
        assertEquals(6,results6);

    }
    @Test
    public void AssignVariables(){
        Date c=new Date();
        String temp1=""+c;
        String results=myHome.AssignVariables(1,2,2019,temp1);
        assertEquals("20190102",results);
        String results1=myHome.AssignVariables(1,11,2019,temp1);
        assertEquals("20190111",results1);
        String results2=myHome.AssignVariables(11,2,2019,temp1);
        assertEquals("20191102",results2);
        String results3=myHome.AssignVariables(11,11,2019,temp1);
        assertEquals("20191111",results3);
    }
    @Test
    public void DateOrder(){
        String Dateone="3";
        String Datetwo="24";
        String results[]=myHome.DateOrder(Dateone,Datetwo);
        String temp[]=new String[2];
        temp[1]="20190524";
        temp[0]="20190503";
        assertEquals(temp,results);

        Dateone="30";
        Datetwo="4";
        results=myHome.DateOrder(Dateone,Datetwo);
        String temp1[]=new String[2];
        temp1[1]="20190604";
        temp1[0]="20190530";
        assertEquals(temp1,results);

    }

}