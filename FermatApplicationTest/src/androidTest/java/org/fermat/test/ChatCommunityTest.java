package org.fermat.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class ChatCommunityTest extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.bitdubai.android_core.app.StartActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public ChatCommunityTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.bitdubai.android_core.app.StartActivity'
		solo.waitForActivity("StartActivity", 2000);
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Set default small timeout to 11198 milliseconds
		Timeout.setSmallTimeout(11198);
        //Assert that: 'ImageView' is shown
		assertTrue("'ImageView' is not shown!", solo.waitForView(solo.getView(android.widget.ImageView.class, 1)));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("radio_fifth"));
        //Click on Got it
		solo.clickOnView(solo.getView("btn_got_it"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView("image_view", 25));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on ImageView
		solo.clickOnView(solo.getView("image_view", 3));
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
		assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Press menu back key
		solo.goBack();
        //Wait for dialog to close
		solo.waitForDialogToClose(5000);
        //Click on Empty Text View
		solo.clickOnView(solo.getView("editTextName"));
        //Enter the text: 'RoyRobotium'
		solo.clearEditText((android.widget.EditText) solo.getView("editTextName"));
		solo.enterText((android.widget.EditText) solo.getView("editTextName"), "RoyRobotium");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("cht_button"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView("image_view", 26));
        //Click on LinearLayout
		solo.clickInRecyclerView(1, 0);
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
		assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));

		/*
		 *	TESTCASE TO REFRESH COMMUNITY FOR 100 TIMES
		 */
		for(int i = 0; i<= 100; i++){
			//Press menu back key
			solo.goBack();
			//Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
			assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
			//Click on LinearLayout
			solo.clickInRecyclerView(1, 0);
			//Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
			assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
		}

        //Click on Add Contact
		solo.clickOnView(solo.getView("add_contact_button"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("positive_button"));
        //Click on  CONNECTIONS
		solo.clickOnText(java.util.regex.Pattern.quote(" CONNECTIONS "));
        //Click on NOTIFICATIONS
		solo.clickOnText(java.util.regex.Pattern.quote("NOTIFICATIONS"));
        //Click on  CONNECTIONS
		solo.clickOnText(java.util.regex.Pattern.quote(" CONNECTIONS "));
        //Click on    BROWSER
		solo.clickOnText(java.util.regex.Pattern.quote("   BROWSER   "));
        //Click on josetest17 Searching... is now a connection Add Contact
		solo.clickInRecyclerView(3, 0);
        //Press menu back key
		solo.goBack();
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on LinearLayout
		solo.clickInRecyclerView(1, 0);
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
		assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView("connectedButton", 3));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("positive_button"));
        //Click on NOTIFICATIONS
		solo.clickOnText(java.util.regex.Pattern.quote("NOTIFICATIONS"));
        //Click on RelativeLayout Roy1 Connection Request
		solo.clickInRecyclerView(0, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("positive_button"));
        //Click on  CONNECTIONS
		solo.clickOnText(java.util.regex.Pattern.quote(" CONNECTIONS "));
        //Click on    BROWSER
		solo.clickOnText(java.util.regex.Pattern.quote("   BROWSER   "));
        //Press menu back key
		solo.goBack();
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on LinearLayout
		solo.clickInRecyclerView(1, 0);
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
		assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        //Click on Show more
		solo.clickOnView(solo.getView("show_more_button"));
        //Click on  CONNECTIONS
		solo.clickOnText(java.util.regex.Pattern.quote(" CONNECTIONS "));
        //Click on NOTIFICATIONS
		solo.clickOnText(java.util.regex.Pattern.quote("NOTIFICATIONS"));
        //Click on    BROWSER
		solo.clickOnText(java.util.regex.Pattern.quote("   BROWSER   "));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on Help
		solo.clickInList(3, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("start_community"));
        //Click on Add Contact
		solo.clickOnView(solo.getView("add_contact_button", 3));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("positive_button"));
        //Press menu back key
		solo.goBack();
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on LinearLayout
		solo.clickInRecyclerView(1, 0);
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
		assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        //Click on Add Contact
		solo.clickOnView(solo.getView("add_contact_button", 3));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("positive_button"));
        //Click on  CONNECTIONS
		solo.clickOnText(java.util.regex.Pattern.quote(" CONNECTIONS "));
        //Click on Roy1 Searching...
		solo.clickInRecyclerView(0, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Press menu back key
		solo.goBack();
        //Wait for dialog to close
		solo.waitForDialogToClose(5000);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on Help
		solo.clickInList(3, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Ok
		solo.clickOnView(solo.getView("start_community"));
        //Click on    BROWSER
		solo.clickOnText(java.util.regex.Pattern.quote("   BROWSER   "));
	}
}
