package com.manoj.transformersae

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.testutill.ElapsedTimeIdlingResource
import com.manoj.transformersae.testutill.TestUtill.Companion.parseSingleTransformerResponse
import com.manoj.transformersae.testutill.TestUtill.Companion.parseTransformersResponseToList
import com.manoj.transformersae.testutill.childOfViewAtPositionWithMatcher
import com.manoj.transformersae.testutill.loadMockData
import com.manoj.transformersae.testutill.waitingTime
import com.manoj.transformersae.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Manoj Vemuru on 2020-06-17.
 * manojvemuru@gmail.com
 */

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun cleanUp() {
        if (::activityScenario.isInitialized)
            activityScenario.close()
    }

    @Test
    fun appLaunchesSuccessfully() {
        onView(withId(R.id.main_list_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun warButtonDisplayTest() {
        onView(withId(R.id.war_button)).check(matches(isDisplayed()))
    }

    @Test
    fun addBotButtonDisplayTest() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewEmptyTest() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        activityScenario.onActivity { mainActivity ->
            val listItems: List<BotModel> = ArrayList()
            mainActivity.updateList(listItems)
        }
        onView(withId(R.id.bot_list)).check(matches(hasMinimumChildCount(0)))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun recyclerViewCountTest() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        activityScenario.onActivity { mainActivity ->
            val loadData = loadMockData("transformers.json", mainActivity)
            val listItems = parseTransformersResponseToList(loadData)
            mainActivity.updateList(listItems)
        }
        onView(withId(R.id.bot_list)).check(matches(hasMinimumChildCount(3)))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun recyclerViewItemContentTest() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        var expectedNameText = ""
        activityScenario.onActivity { mainActivity ->
            val loadData = loadMockData("HubcapTransformer.json", mainActivity)
            val listItem = parseSingleTransformerResponse(loadData)
            expectedNameText = listItem.name
            val listItems = ArrayList<BotModel>().apply { this.add(listItem) }
            mainActivity.updateList(listItems)
        }
        onView(withId(R.id.bot_list)).check(matches(hasMinimumChildCount(1)))
        onView(withId(R.id.bot_list)).check(matches(childOfViewAtPositionWithMatcher(R.id.name, 0, withText(expectedNameText))))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun resultDialogContentTest() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(waitingTime)
        val expectedText = "Winning team (Decepticons): Soundwave"
        IdlingRegistry.getInstance().register(idlingResource)
        activityScenario.onActivity { mainActivity ->
            val dialog = mainActivity.getDialogView()
            val text = dialog.findViewById(R.id.content) as TextView
            text.text = expectedText
           dialog.show()
        }
        onView(withId(R.id.dialog_parent_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.ok_button)).check(matches(isDisplayed()))
        onView(withId(R.id.content)).check(matches(withText(expectedText)))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}