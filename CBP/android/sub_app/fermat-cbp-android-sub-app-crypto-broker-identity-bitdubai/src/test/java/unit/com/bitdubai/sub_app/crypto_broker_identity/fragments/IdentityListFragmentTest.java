package unit.com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;


import com.bitdubai.sub_app.crypto_broker_identity.BuildConfig;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.CryptoBrokerIdentityListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import unit.com.bitdubai.sub_app.crypto_broker_identity.TestActivity;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class IdentityListFragmentTest {

    private CryptoBrokerIdentityListFragment fragment;
    private TestActivity activity;

    @Before
    public void setUp() {
        fragment = CryptoBrokerIdentityListFragment.newInstance();

        activity = Robolectric.setupActivity(TestActivity.class);

        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.add(fragment, null);
        ft.commit();
    }


    @Test
    public void fragmentIsVisibleInActivity() {
        Activity resultActivity = fragment.getActivity();
        assertThat(resultActivity).isInstanceOf(TestActivity.class);
        assertThat(fragment.isVisible()).isTrue();
    }

    @Test
    public void testShowOneElementInList(){

    }

    @Test
    public void testNoShowElementsInList(){

    }

    @Test
    public void clickOnAddIdentityButtonSendToCreateIdentityFragment(){

    }

    @Test
    public void clickOnListTtemShowIdentityDetailsFragment(){

    }


}