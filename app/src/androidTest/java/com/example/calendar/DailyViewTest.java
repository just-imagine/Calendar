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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DailyViewTest {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityTestRule = new ActivityTestRule<>(HomeScreen.class);

    @Test
    public void testForFreeSlot() {

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

        ViewInteraction directionButton2 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton2.perform(click());

        ViewInteraction directionButton3 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton3.perform(click());

        ViewInteraction directionButton4 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton4.perform(click());

        ViewInteraction directionButton5 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton5.perform(click());

        ViewInteraction directionButton6 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton6.perform(click());

        ViewInteraction directionButton7 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton7.perform(click());

        ViewInteraction dayView = onView(
                allOf(withText("2"), withContentDescription("2"),
                        childAtPosition(
                                allOf(withContentDescription("Calendar"),
                                        childAtPosition(
                                                withId(R.id.mcv_pager),
                                                1)),
                                8),
                        isDisplayed()));
        dayView.perform(longClick());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.g), withText("Free"),
                        childAtPosition(
                                allOf(withId(R.id.l),
                                        childAtPosition(
                                                withId(R.id.sample),
                                                0)),
                                2)));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.content),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.cancel), withText("Block"),
                        childAtPosition(
                                allOf(withId(R.id.pop),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                4)),
                                1),
                        isDisplayed()));
//        textView.check(matches(withText("Block")));
    }
    @Test
    public void testForBookedSlot()
    {
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

        ViewInteraction directionButton2 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton2.perform(click());

        ViewInteraction directionButton3 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton3.perform(click());

        ViewInteraction directionButton4 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton4.perform(click());

        ViewInteraction directionButton5 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton5.perform(click());

        ViewInteraction directionButton6 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton6.perform(click());

        ViewInteraction directionButton7 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton7.perform(click());

        ViewInteraction dayView = onView(
                allOf(withText("3"), withContentDescription("3"),
                        childAtPosition(
                                allOf(withContentDescription("Calendar"),
                                        childAtPosition(
                                                withId(R.id.mcv_pager),
                                                1)),
                                9),
                        isDisplayed()));
        dayView.perform(longClick());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.g), withText("Appointment"),
                        childAtPosition(
                                allOf(withId(R.id.l),
                                        childAtPosition(
                                                withId(R.id.sample),
                                                0)),
                                2)));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.content),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.patient), withText("Bafana  Mbata"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Bafana  Mbata")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.timedetails), withText("Tue , 3 December\n\nDuration 08:00-08:15"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                0),
                        isDisplayed()));
//        textView2.check(matches(withText("Tue , 3 December  Duration 08:00-08:15")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.email), withText("1629230@students.wits.ac.za"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("1629230@students.wits.ac.za")));
    }
   //@Test
    public void testForCheckoutToast() {
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

        ViewInteraction directionButton2 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton2.perform(click());

        ViewInteraction directionButton3 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton3.perform(click());

        ViewInteraction directionButton4 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton4.perform(click());

        ViewInteraction directionButton5 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton5.perform(click());

        ViewInteraction directionButton6 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton6.perform(click());

        ViewInteraction directionButton7 = onView(
                allOf(withContentDescription("Go to next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.thing),
                                        0),
                                2),
                        isDisplayed()));
        directionButton7.perform(click());

        ViewInteraction dayView = onView(
                allOf(withText("3"), withContentDescription("3"),
                        childAtPosition(
                                allOf(withContentDescription("Calendar"),
                                        childAtPosition(
                                                withId(R.id.mcv_pager),
                                                1)),
                                9),
                        isDisplayed()));
        dayView.perform(longClick());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.g), withText("Appointment"),
                        childAtPosition(
                                allOf(withId(R.id.l),
                                        childAtPosition(
                                                withId(R.id.sample),
                                                0)),
                                2)));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.checkout), withText("Checkout"),
                        childAtPosition(
                                allOf(withId(R.id.pop),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.col),
                                12),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
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