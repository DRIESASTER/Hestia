package com.ugnet.sel1

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.manager.IssueData
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

fun createMockIssueDataList(): List<IssueData> {
    val mockIssueDataList = mutableListOf<IssueData>()

    for (i in 0..2) {
        val issueData = IssueData(
            id = i.toString(),
            title = "title${i}",
            description = "decr${i}",
            status = Status.notStarted,
            tenant = "tenant${i}",
            room = "room${i}"
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
            IssueOverview(issues = mockIssueDataList, onIssueClicked = {/* Do nothing */})
        }

        // Check if each property is displayed properly
        mockIssueDataList.forEach { issueData:IssueData ->
            composeTestRule.onNodeWithText(issueData.title).assertIsDisplayed()
            composeTestRule.onNodeWithText(issueData.description).assertIsDisplayed()
            composeTestRule.onNodeWithText(issueData.tenant).assertIsDisplayed()
            composeTestRule.onNodeWithText(issueData.room).assertIsDisplayed()

        }
    }
}


