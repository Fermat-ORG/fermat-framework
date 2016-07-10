//package com.bitdubai.fermat.test;
//
//import com.bitdubai.android_core.app.StartActivity;
//import com.bitdubai.fermat.R;
//import com.robotium.solo.*;
//
//import android.app.Activity;
//import android.test.ActivityInstrumentationTestCase2;
//import android.view.View;
//
//
//public class BitcoinReferenceWalletTest extends ActivityInstrumentationTestCase2<StartActivity> {
//
//	private static final String TEST_NAME_1 = "TEST NAME 1";
//	private static final String TEST_NAME_2 = "TEST NAME 2";
//	private static final String TEST_ADDRESS_1 = "mjoLkvAgA8mvq9P27zk52LmHJcJEUD5E4U";
//	private static final String TEST_ADDRESS_2 = "n3TgXMCguvAjNoCCovLWTegR9PjoWfim5h";
//	private static final String TEST_NAME_PARTIAL = "TEST";
//
//	private Solo solo;
//
//  	public BitcoinReferenceWalletTest() {
//		super(StartActivity.class);
//  	}
//
//  	public void setUp() throws Exception {
//        super.setUp();
//		solo = new Solo(getInstrumentation());
//		getActivity();
//  	}
//
//   	@Override
//   	public void tearDown() throws Exception {
//        solo.finishOpenedActivities();
//        super.tearDown();
//  	}
//
//
//	public void testRun() {
//        solo.waitForActivity(com.bitdubai.android_core.app.StartActivity.class, 5000);
//        assertTrue("com.bitdubai.android_core.app.SubAppActivity is not found!", solo.waitForActivity(com.bitdubai.android_core.app.SubAppActivity.class));
//
//		balanceTest();
//		receiveTest(TEST_NAME_1);
//		addContactTest(TEST_NAME_1, TEST_ADDRESS_1);
//		addContactTest(TEST_NAME_2, TEST_ADDRESS_2);
//		sendTest(TEST_NAME_PARTIAL);
//
//        assertTrue("com.bitdubai.android_core.app.SubAppActivity is not found!", solo.waitForActivity(com.bitdubai.android_core.app.SubAppActivity.class));
//	}
//
//	private void enterWallet(){
//		assertTrue("com.bitdubai.android_core.app.SubAppActivity is not found!", solo.waitForActivity(com.bitdubai.android_core.app.SubAppActivity.class));
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.image_view));
//		assertTrue("com.bitdubai.android_core.app.WalletActivity is not found!", solo.waitForActivity(com.bitdubai.android_core.app.WalletActivity.class));
//	}
//
//	private void balanceTest(){
//		enterWallet();
//		for(int i = 0; i < 10; ++i) {
//			solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.txtViewTypeBalance));
//			solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.txtViewTypeBalance));
//		}
//		solo.goBack();
//	}
//
//	private void receiveTest(final String name){
//		enterWallet();
//		solo.clickOnText(java.util.regex.Pattern.quote("Receive"));
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.autocomplete_contacts));
//		solo.clearEditText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.autocomplete_contacts));
//		solo.enterText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.autocomplete_contacts), name);
//		for(int i = 0; i < 20; ++i)
//			solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.request_btn));
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.string_address));
//		solo.goBack();
//	}
//
//	private void addContactTest(final String name, final String address){
//		enterWallet();
//		solo.scrollViewToSide(solo.getView(com.bitdubai.fermat.R.id.tabs), Solo.RIGHT, 0.416F);
//		solo.clickOnText(java.util.regex.Pattern.quote("Contacts"));
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.fab_add_person));
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.contact_name));
//		solo.clearEditText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.contact_name));
//		solo.enterText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.contact_name), name);
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.contact_address));
//		solo.clearEditText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.contact_address));
//		solo.enterText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.contact_address), address);
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.save_contact_btn));
//		solo.goBack();
//	}
//
//	private void sendTest(final String partialName){
//		enterWallet();
//		solo.clickOnText(java.util.regex.Pattern.quote("Send"));
//		solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.contact_name));
//		solo.clearEditText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.contact_name));
//		solo.enterText((android.widget.EditText) solo.getView(com.bitdubai.fermat.R.id.contact_name), partialName);
//		solo.clickInList(1, 0);
//		for(int i = 0; i < 20; ++i)
//			solo.clickOnView(solo.getView(com.bitdubai.fermat.R.id.send_button));
//		solo.goBack();
//	}
//}


