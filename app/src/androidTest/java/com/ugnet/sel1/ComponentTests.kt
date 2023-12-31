package com.ugnet.sel1

import android.util.Log
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ugnet.sel1.ui.components.ProgressSwitch
import com.ugnet.sel1.ui.components.PropertyCard
import com.ugnet.sel1.ui.components.SwitchButton2
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ComponentTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun SwitchButton2Test() {
        var state = true
        composeTestRule.setContent {

            SwitchButton2(

                initialState = state,
                onStateChanged = { state = it }
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

    @Test
    fun ProgressionSwitchTest() {
        var state = "not started"
        composeTestRule.setContent {

            ProgressSwitch(
                option1 = "not started",
                option2 = "in progress",
                option3 = "finished",
                initialState = state
            ) { state = it }
        }

        composeTestRule.onNodeWithText("not started").assertIsDisplayed()
        composeTestRule.onNodeWithText("in progress").assertIsDisplayed()
        composeTestRule.onNodeWithText("finished").assertIsDisplayed()
        composeTestRule.onNodeWithText("in progress").performClick()
        assert(state == "in progress") { "State should be in progress" }
        composeTestRule.onNodeWithText("finished").performClick()
        assert(state == "finished") { "State should be finished" }
        composeTestRule.onNodeWithText("not started").performClick()
        assert(state == "not started") { "State should be not started" }

    }

    @Test
    fun PropertyCardTest(){
        composeTestRule.setContent {
            PropertyCard("big test house", "test street"+9+" ,"+ 9000 +" "+ "test city",  4, 10, {}, onDelete = {}, onEdit = {})
        }

        composeTestRule.onNodeWithText("big test house").assertIsDisplayed()
        composeTestRule.onNodeWithText("test street9 ,9000 test city").assertIsDisplayed()
        composeTestRule.onNodeWithText("tenants: 4").assertIsDisplayed()
        composeTestRule.onNodeWithText("issues: 10").assertIsDisplayed()
        (composeTestRule.onNodeWithText("big test house")).assertHasClickAction()
    }

}
