package org.fermat.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class ChatIdentityTest extends ActivityInstrumentationTestCase2 {
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
    public ChatIdentityTest() throws ClassNotFoundException {
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
	}
}
