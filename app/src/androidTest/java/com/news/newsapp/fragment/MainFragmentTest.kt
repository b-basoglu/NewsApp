package com.news.newsapp.fragment

import android.accessibilityservice.AccessibilityService
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.news.newsapp.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.news.newsapp.R

@HiltAndroidTest
class MainFragmentTest {
    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)
    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Before
    fun jumpToMainFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.container).navigate(R.id.main_fragment)
            }
        }
    }
    @Test
    fun testListClickAndBack() {
        Intents.init()
        Espresso.onView(withId(R.id.rv_news))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ));
        Espresso.onView(withId(R.id.web_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)

    }

}