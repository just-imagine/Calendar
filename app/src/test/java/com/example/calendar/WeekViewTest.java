package com.example.calendar;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

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

}