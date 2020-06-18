package com.manoj.transformersae

import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior
import com.manoj.transformersae.testutill.ElapsedTimeIdlingResource
import com.manoj.transformersae.testutill.hideKeyboard
import com.manoj.transformersae.testutill.viewMatcherWithText
import com.manoj.transformersae.testutill.waitingTime
import com.manoj.transformersae.ui.custom.CriteriaComponent
import com.manoj.transformersae.ui.detailview.ItemDetailActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Manoj Vemuru on 2020-06-17.
 * manojvemuru@gmail.com
 */
@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    private lateinit var activityScenario: ActivityScenario<ItemDetailActivity>

    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(ItemDetailActivity::class.java)
    }

    @After
    fun cleanUp() {
        if (::activityScenario.isInitialized)
            activityScenario.close()
    }

    @Test
    fun onLaunchSaveButtonIsDisplayed() {
        onView(withId(R.id.button_save)).check(matches(isDisplayed()))
        val inputText = "Update"

        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.button_save)).perform(object : ViewAction {
            override fun getDescription(): String = "Update Text"

            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun perform(uiController: UiController, view: View) {
                (view as Button).text = inputText
            }
        })
        onView(withId(R.id.button_save)).check(matches(withText(inputText)))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onLaunchNameEditTextTest() {
        onView(withId(R.id.name_edit_text)).check(matches(isDisplayed()))
        val inputText = "SimpleText"
        onView(withId(R.id.name_edit_text)).perform(typeText(inputText))
        onView(withId(R.id.name_edit_text)).check(matches(withText(inputText)))
    }

    @Test
    fun onLaunchRadioButtonDisplayed() {
        onView(withId(R.id.radio_group)).check(matches(isDisplayed()))
        onView(withId(R.id.radio_d)).check(matches(isDisplayed()))
        onView(withId(R.id.radio_a)).check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchRadioButtonChangesTest() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.radio_d)).perform(object : ViewAction {
            override fun getDescription(): String = "Checked"

            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun perform(uiController: UiController, view: View) {
                (view as RadioButton).isChecked = true
            }
        })
        onView(withId(R.id.radio_d)).check(matches(isChecked()))
        IdlingRegistry.getInstance().unregister(idlingResource)

        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.radio_a)).perform(object : ViewAction {
            override fun getDescription(): String = "Checked"

            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun perform(uiController: UiController, view: View) {
                (view as RadioButton).isChecked = true
            }
        })
        onView(withId(R.id.radio_a)).check(matches(isChecked()))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }


    @Test
    fun onLaunchCriteriaViewDisplayTest() {
        onView(withId(R.id.strength)).check(matches(isDisplayed()))
        onView(withId(R.id.intelligence)).check(matches(isDisplayed()))
        onView(withId(R.id.speed)).check(matches(isDisplayed()))
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        activityScenario.onActivity { detailedActivity ->
            detailedActivity.setupBarViewForTesting()
            hideKeyboard(detailedActivity)
            (detailedActivity.findViewById(R.id.item_detail_container) as NestedScrollView).fullScroll(View.FOCUS_DOWN);
        }
        onView(withId(R.id.endurance)).check(matches(isDisplayed()))
        onView(withId(R.id.rank)).check(matches(isDisplayed()))
        onView(withId(R.id.courage)).check(matches(isDisplayed()))
        onView(withId(R.id.firepower)).check(matches(isDisplayed()))
        onView(withId(R.id.skill)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onLaunchCriteriaTest() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.strength)).perform(object : ViewAction {
            override fun getDescription(): String = "strength"

            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as CriteriaComponent).setValues("Strength", 8, false)
            }

        })
        onView(viewMatcherWithText("Strength", "8")).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}