
package org.fermat.test;

import android.test.ActivityInstrumentationTestCase2;

//import com.robotium.solo.Solo;
//import com.robotium.solo.Timeout;


@SuppressWarnings("rawtypes")
public class primer_test extends ActivityInstrumentationTestCase2 {
  	//private Solo solo;

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
    public primer_test() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		//solo = new Solo(getInstrumentation());
		getActivity();
  	}

   	@Override
   	public void tearDown() throws Exception {
       // solo.finishOpenedActivities();
        super.tearDown();
  	}

	public void testRun() {
        Wait for activity: 'com.bitdubai.android_core.app.StartActivity'
		solo.waitForActivity("StartActivity", 2000);
        Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
        Set default small timeout to 66809 milliseconds
		Timeout.setSmallTimeout(66809);
//        Click on Next
		solo.clickOnView(solo.getView("btn_got_it"));
//        Click on Next
		solo.clickOnView(solo.getView("btn_got_it"));
//        Click on Next
		solo.clickOnView(solo.getView("btn_got_it"));
//        Click on Next
		solo.clickOnView(solo.getView("btn_got_it"));
//        Click on Got it
		solo.clickOnView(solo.getView("btn_got_it"));
        Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
//        Click on ImageView
		solo.clickOnView(solo.getView("image_view", 26));
        Wait for activity: 'com.bitdubai.android_core.app.DesktopActivity'
		assertTrue("DesktopActivity is not found!", solo.waitForActivity("DesktopActivity"));
//        Click on LinearLayout
		solo.clickInRecyclerView(3, 0);
        Wait for activity: 'com.bitdubai.android_core.app.AppActivity'
		assertTrue("AppActivity is not found!", solo.waitForActivity("AppActivity"));
        Wait for dialog
//        Click on Be John Doe
		try {
			//assertTrue("DesktopActivity is not found!", solo.waitForDialogToOpen(5000));
			//solo.clickOnView(solo.getView("btn_left"));
		}catch (Exception e){
			e.printStackTrace();
		}
		try {
			solo.wait(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
	}
}

