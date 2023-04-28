package com.ugnet.sel1

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.ui.manager.IssueOverview
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ugnet.sel1", appContext.packageName)
    }
}

fun createMockIssueDataList(): List<Property> {
    val mockIssueDataList = mutableListOf<Property>()

    for (i in 0..2) {
        val issueData = Property(
            propertyId = "propertyId$i",
            straat = "address$i",
            huisnummer = 9,
            postcode = 900,
            stad = "city$i",
            ownedBy = "owner$i",
            type = "type$i",
            huurders = listOf("tenant$i"),
                )
        mockIssueDataList.add(issueData)
    }

    return mockIssueDataList
}

class IssueOverviewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun issueOverviewDisplaysDataProperly() {
        val mockIssueDataList = createMockIssueDataList()

        // Set up your Composable under test
        composeTestRule.setContent {
            IssueOverview(properties = mockIssueDataList, onIssueClicked = {/* Do nothing */}, onStatusClicked = { _, _, _ -> /* Do nothing */}, viewModel = viewModel())
        }

        // Check if each property is displayed properly
        mockIssueDataList.forEach { issueData:Property ->
            composeTestRule.onNodeWithText(issueData.straat!!).assertIsDisplayed()
            composeTestRule.onNodeWithText(issueData.stad!!).assertIsDisplayed()
            composeTestRule.onNodeWithText(issueData.propertyId!!).assertIsDisplayed()
            composeTestRule.onNodeWithText(issueData.ownedBy!!).assertIsDisplayed()

        }
    }
}


