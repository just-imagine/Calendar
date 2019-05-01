package com.example.calendar;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeScreenTest {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityTestRule = new ActivityTestRule<>(HomeScreen.class);
    @Test
    public void testForDayClick(){
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction dayView = onView(
                allOf(withText("2"), withContentDescription("2"),
                        childAtPosition(
                                allOf(withContentDescription("Calendar"),
                                        childAtPosition(
                                                withId(R.id.mcv_pager),
                                                0)),
                                11),
                        isDisplayed()));
        dayView.perform(longClick());

        ViewInteraction textView = onView(
                allOf(withId(R.id.slotOne), withText("08:00 AM"),
                        childAtPosition(
                                allOf(withId(R.id.l),
                                        childAtPosition(
                                                withId(R.id.sample),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("08:00 AM")));
    }
    @Test
    public void testForMonthChange() {

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction directionButton = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("June 2019"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("June 2019")));

        ViewInteraction directionButton2 = onView(
                allOf(withContentDescription("Go to previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                0),
                        isDisplayed()));
        directionButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView2 = onView(
                allOf(withText("May 2019"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("May 2019")));
    }
    @Test
    public void testForImageDisplay(){
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageView = onView(
                allOf(withId(R.id.display),
                        childAtPosition(
                                allOf(withId(R.id.card1),
                                        childAtPosition(
                                                withId(R.id.viewOne),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
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
