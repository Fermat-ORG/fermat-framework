package org.fermat.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class ChatChatTest extends ActivityInstrumentationTestCase2 {
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
    public ChatChatTest() throws ClassNotFoundException {
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

        //IDENTITY TEST
        //Wait for activity: 'com.bitdubai.android_core.app.StartActivity'
        solo.waitForActivity("StartActivity", 2000);
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Got it
        solo.clickOnView(solo.getView("btn_got_it"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
        //Click on Next
        solo.clickOnView(solo.getView("btn_got_it"));
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
        //Click on Close
        solo.clickOnView(solo.getView("btn_dismiss"));
        //Set default small timeout to 13334 milliseconds
        Timeout.setSmallTimeout(13334);
        //Enter the text: 'test'
        solo.clearEditText((android.widget.EditText) solo.getView("editTextName"));
        solo.enterText((android.widget.EditText) solo.getView("editTextName"), "test");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("editTextStatus"));
        //Click on ImageView
        solo.clickOnView(solo.getView("cht_image"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Wait for dialog to close
        solo.waitForDialogToClose(5000);
        //Set default small timeout to 26947 milliseconds
        Timeout.setSmallTimeout(26947);
        //Enter the text: 'available'
        solo.clearEditText((android.widget.EditText) solo.getView("editTextStatus"));
        solo.enterText((android.widget.EditText) solo.getView("editTextStatus"), "available");
        //Click on ImageView
        solo.clickOnView(solo.getView("cht_image"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on ImageView
        solo.clickOnView(solo.getView("img_cam"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Rotate
        solo.clickOnView(solo.getView("btnRotateCropper"));
        //Click on Rotate
        solo.clickOnView(solo.getView("btnRotateCropper"));
        //Click on Ok
        solo.clickOnView(solo.getView("btnCrop"));
        //Click on ImageView
        solo.clickOnView(solo.getView("cht_image"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on ImageView
        solo.clickOnView(solo.getView("img_cam"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Ok
        solo.clickOnView(solo.getView("btnCrop"));
        //Click on ImageView
        solo.clickOnView(solo.getView("cht_image"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on ImageView
        solo.clickOnView(solo.getView("img_gallery"));
        //Wait for activity: 'com.android.internal.app.ChooserActivity'
        assertTrue("ChooserActivity is not found!", solo.waitForActivity("ChooserActivity"));
        //Click on Gallery
        solo.clickInList(2, 0);
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Rotate
        solo.clickOnView(solo.getView("btnRotateCropper"));
        //Click on Ok
        solo.clickOnView(solo.getView("btnCrop"));
        //Click on Empty Text View
        solo.clickOnView(solo.getView("cht_button"));
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
        //Click on Empty Text View
        solo.clickOnView(solo.getView(0x1));
        //Click on accuracy
        solo.clickOnView(solo.getView("accuracy"));
        //Enter the text: '10'
        solo.clearEditText((android.widget.EditText) solo.getView("accuracy"));
        solo.enterText((android.widget.EditText) solo.getView("accuracy"), "10");
        //Click on NONE
        solo.clickOnView(solo.getView("spinner_frequency"));
        //Click on HIGH
        solo.clickOnView(solo.getView(android.R.id.text1, 2));
        //Click on ImageView
        solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
        solo.clickOnView(solo.getView("cht_image"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on ImageView
        solo.clickOnView(solo.getView("img_cam"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Rotate
        solo.clickOnView(solo.getView("btnRotateCropper"));
        //Click on Rotate
        solo.clickOnView(solo.getView("btnRotateCropper"));
        //Click on Ok
        solo.clickOnView(solo.getView("btnCrop"));
        //Click on ImageView
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on Help
        solo.clickInList(1, 0);
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Close
        solo.clickOnView(solo.getView("btn_dismiss"));
        //Click on Save Changes
        solo.clickOnView(solo.getView("cht_button"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));

        //COMMUNITY TEST
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

        //CHAT TEST
        //Wait for activity: 'com.bitdubai.android_core.app.StartActivity'
        solo.waitForActivity("StartActivity", 2000);
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Set default small timeout to 11198 milliseconds
        Timeout.setSmallTimeout(11198);
        //Click on Empty Text View
        solo.clickOnView(solo.getView("radio_fifth"));
        //Click on Got it
        solo.clickOnView(solo.getView("btn_got_it"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
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
        //Click on Close
        solo.clickOnView(solo.getView("btn_dismiss"));
        //Click on Empty Text View
        solo.clickOnView(solo.getView("editTextName"));
        //Enter the text: 'RoyRobotium3'
        solo.clearEditText((android.widget.EditText) solo.getView("editTextName"));
        solo.enterText((android.widget.EditText) solo.getView("editTextName"), "RoyRobotium3");
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
        //Click on Show more
        solo.clickOnView(solo.getView("show_more_button"));
        //Click on NOTIFICATIONS
        solo.clickOnText(java.util.regex.Pattern.quote("NOTIFICATIONS"));
        //Click on RelativeLayout RoyPar Connection Request
        solo.clickInRecyclerView(0, 0);
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Ok
        solo.clickOnView(solo.getView("positive_button"));
        //Press menu back key
        solo.goBack();
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on ImageView
        solo.clickOnView(solo.getView("btn_close"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on ImageView
        solo.clickOnView(solo.getView("image_view"));
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
        assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Close
        solo.clickOnView(solo.getView("btn_dismiss"));
        //Click on RoyPar Hola 05:49 PM 1
        solo.clickInList(1, 0);
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Enter the text: 'Alo'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Alo");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));


		/*
			TEST CASE: SEND MESSAGES FOR ABOUT FOUR HOURS
		 */
        for (int i = 0; i <= 15000; i++){
            //Click on Empty Text View
            solo.clickOnView(solo.getView("messageEdit"));
            //Enter the text: 'Mensaje 2'
            solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
            solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Mensaje " + "i");
            //Click on Empty Text View
            solo.clickOnView(solo.getView("chatSendButton"));
        }


        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Enter the text: 'Mensaje 2'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Mensaje 2");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Click on Alo 05:49 PM
        solo.clickInRecyclerView(1, 0);
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Enter the text: 'Hola3'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Hola3");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Enter the text: 'Hola4'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Hola4");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Enter the text: 'Hola5'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Hola5");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Enter the text: '132321321321321321321321321321321321321321321321321321321321321321321321321'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "132321321321321321321321321321321321321321321321321321321321321321321321321321321321321321321321321");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Enter the text: 'kjhkjhjhljhlkhlkjhljhlkjlkjhlkjhlkjhljhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjh'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "kjhkjhjhljhlkhlkjhljhlkjlkjhlkjhlkjhljhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhjhlakdjfhasldkfjahsdlfkjahsdflakjshdflaskdjfhsldkfjhasdlfkajhsflaksjdhf");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Long click kjhkjhjhljhlkhlkjhljhlkjlkjhlkjhlkjhljhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjh
        solo.clickLongOnView(solo.getView("content", 3));
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Click on Paste
        solo.clickOnText(java.util.regex.Pattern.quote("Paste"));
        //Enter the text: 'kjhkjhjhljhlkhlkjhljhlkjlkjhlkjhlkjhljhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjh'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "kjhkjhjhljhlkhlkjhljhlkjlkjhlkjhlkjhljhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhlkjhjhlakdjfhasldkfjahsdlfkjahsdflakjshdflaskdjfhsldkfjhasdlfkajhsflaksjdhf");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Click on ImageView
        solo.clickOnView(solo.getView("search_button"));
        //Enter the text: 'hola'
        solo.clearEditText((android.widget.EditText) solo.getView("search_src_text"));
        solo.enterText((android.widget.EditText) solo.getView("search_src_text"), "hola");
        //Long click Hola 05:49 PM
        solo.clickLongOnView(solo.getView("content"));
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Click on Paste
        solo.clickOnText(java.util.regex.Pattern.quote("Paste"));
        //Enter the text: 'Hola'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Hola");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Click on Paste
        solo.clickOnText(java.util.regex.Pattern.quote("Paste"));
        //Enter the text: 'Hola'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Hola");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
        //Click on ImageView
        solo.clickOnView(solo.getView("search_close_btn"));
        //Click on ImageView
        solo.clickOnView(solo.getView("search_close_btn"));
        //Click on ImageView
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 2));
        //Click on ImageView
        solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on ImageView
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 1));
        //Click on Delete All Chats
        solo.clickInList(1, 0);
        //Wait for dialog
        solo.waitForDialogToOpen(5000);
        //Click on Yes
        solo.clickOnView(solo.getView("cht_alert_btn_yes"));
        //Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
        assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        //Click on ImageView
        solo.clickOnView(solo.getView("image_view"));
        //Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
        assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        //Click on RoyPar fasdfasdfsadfsadfasdf 05:59 PM 1
        solo.clickInList(1, 0);
        //Click on Empty Text View
        solo.clickOnView(solo.getView("messageEdit"));
        //Enter the text: 'Recibido desde Fermat Desktop'
        solo.clearEditText((android.widget.EditText) solo.getView("messageEdit"));
        solo.enterText((android.widget.EditText) solo.getView("messageEdit"), "Recibido desde Fermat Desktop");
        //Click on Empty Text View
        solo.clickOnView(solo.getView("chatSendButton"));
    }
}
