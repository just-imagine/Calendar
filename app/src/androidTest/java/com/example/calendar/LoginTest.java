package com.example.calendar;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

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
        onView(withId(R.id.loginbut)).perform(scrollTo(),click());
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
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

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
