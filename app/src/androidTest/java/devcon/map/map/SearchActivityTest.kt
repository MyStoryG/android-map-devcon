package devcon.map.map

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import devcon.map.R
import devcon.map.feature.SearchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(SearchActivity::class.java)

    @Test
    fun entry() {
        onView(withId(R.id.textview_no_match_results))
            .check(matches(isDisplayed()))
    }

    @Test
    fun typeKeyword() {
        onView(withId(R.id.edittext_search))
            .perform(typeText("Cafe"))
            .check(matches(withText("Cafe")))
    }
}
