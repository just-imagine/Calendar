package com.example.calendar;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<Login> LoginRule=new ActivityTestRule<>(Login.class);
    @Before
    public void setUp(){
        LoginRule.getActivity();
    }
    @Test
    public void testforEmptyUsernameandpassword(){
        onView(withId(R.id.user)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.loginbut)).perform(click());
        onView(withId(R.id.user)).check(matches(hasErrorText("Enter username.")));
        onView(withId(R.id.password)).check(matches(hasErrorText("Enter password.")));
    }
    @Test
    public void testforEmptyusername(){
        onView(withId(R.id.user)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("assda"),closeSoftKeyboard());
        onView(withId(R.id.loginbut)).perform(click());
        onView(withId(R.id.user)).check(matches(hasErrorText("Enter username.")));
    }
    @Test
    public void testforEmptypassword(){
        onView(withId(R.id.user)).perform(typeText("asdasd"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.loginbut)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("Enter password.")));
    }
    @Test
    public void testforsuccessfulLogin(){
        onView(withId(R.id.user)).perform(typeText("Admin"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("Admin"),closeSoftKeyboard());
        onView(withId(R.id.loginbut)).perform(click());
        onView(withId(R.id.Schedule1)).check(matches(withText("Schedule For the day")));
    }
    @Test
    public void testforIncorrectCredentials(){
        onView(withId(R.id.user)).perform(typeText("hello"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("admin"),closeSoftKeyboard());
        onView(withId(R.id.loginbut)).perform(click());
        Login activity = LoginRule.getActivity();
        onView(withText("Incorrect Credentials")).
                inRoot(withDecorView(CoreMatchers.not(CoreMatchers.is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}