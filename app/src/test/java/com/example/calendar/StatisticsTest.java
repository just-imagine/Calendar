package com.example.calendar;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import static org.junit.Assert.*;


    @RunWith(RobolectricTestRunner.class)
    public class StatisticsTest {


        Statistics myHome;

        @Before
        public void setUp() throws Exception {
            Intent intent = new Intent(RuntimeEnvironment.application, HomeScreen.class);
            ArrayList<Integer>stats=new ArrayList<>();
            stats.add(4);
            stats.add(6);
            intent.putIntegerArrayListExtra("Statistics",stats);
            intent.putExtra("Month", "June");
            myHome = Robolectric.buildActivity(Statistics.class, intent).setup().get();

        }

        @After
        public void tearDown() throws Exception {
        }

        @Test
        public void check() {
            assertNotNull(myHome);


        }

    }