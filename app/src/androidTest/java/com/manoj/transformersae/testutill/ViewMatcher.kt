package com.manoj.transformersae.testutill

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.manoj.transformersae.ui.custom.CriteriaComponent
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.CoreMatchers.allOf

/**
 * Created by Manoj Vemuru on 2020-06-17.
 * manojvemuru@gmail.com
 */

fun viewMatcherWithText(titleText: String, text: String): Matcher<View> {
    return object : BoundedMatcher<View, CriteriaComponent>(CriteriaComponent::class.java) {
        override fun describeTo(description: Description?) {
            //fill these out properly so your logging and error reporting is more clear
            description?.appendText("checking CriteriaComponent")
        }

        override fun matchesSafely(item: CriteriaComponent?): Boolean {
            return item?.criteriaName == titleText && item.criteriaValue.toString() == text
        }
    }
}

/**
 * checks that the matcher childMatcher matches a view having a given id
 * inside a RecyclerView's item (given its position)
 */
fun childOfViewAtPositionWithMatcher(childId: Int, position: Int, childMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("Checks that the matcher childMatcher matches" +
                    " with a view having a given id inside a RecyclerView's item (given its position)")
        }

        override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
            val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
            val matcher = hasDescendant(allOf(withId(childId), childMatcher))
            return viewHolder != null && matcher.matches(viewHolder.itemView)
        }

    }
}