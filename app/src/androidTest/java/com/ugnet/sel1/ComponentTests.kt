package com.ugnet.sel1

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ugnet.sel1.ui.components.SwitchButton2
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ComponentTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun SwitchButton2Test(){
        var state = true
        composeTestRule.setContent {

            SwitchButton2(

                initialState = state,
                onStateChanged = { state=it}
            )
        }
        Log.d("SwitchButton2Test", "state1: $state")
        composeTestRule.onNodeWithText("Issues").assertIsDisplayed()
        composeTestRule.onNodeWithText("Properties").assertIsDisplayed()
        composeTestRule.onNodeWithText("Issues").performClick()
        Log.d("SwitchButton2Test", "state2: $state")
        assert(!state) { "State should be true" }
        composeTestRule.onNodeWithText("Properties").performClick()
        Log.d("SwitchButton2Test", "state3: $state")
        assert(state) { "State should be false" }
    }

}