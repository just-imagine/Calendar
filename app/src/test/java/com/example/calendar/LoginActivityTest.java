package com.example.calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {


    Login Login;

    @Before
    public void setUp() throws Exception {
        Login = Robolectric.setupActivity(Login.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void check() {

        assertNotNull(Login);
    }
    @Test
    public void checkUser() {
        Login.username.setText("Admin");
        Login.findViewById(R.id.loginbut).performClick();
    }
    @Test
    public void checkUser2() {
        Login.username.setText("Admin");
        Login.password.setText("");
        Login.findViewById(R.id.loginbut).performClick();
    }
    @Test
    public void checkUser3() {
        Login.username.setText("");
        Login.password.setText("");
        Login.findViewById(R.id.loginbut).performClick();

    }
}