package unit.com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;

import com.bitdubai.sub_app.crypto_broker_identity.BuildConfig;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.CreateIdentityFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import unit.com.bitdubai.sub_app.crypto_broker_identity.TestActivity;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 09/10/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CreateIdentityFragmentTest {

    private CreateIdentityFragment fragment;
    private TestActivity activity;

    @Before
    public void setUp() throws Exception {
        fragment = CreateIdentityFragment.newInstance();
        activity = Robolectric.setupActivity(TestActivity.class);

        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.add(TestActivity.LAYOUT_ID, fragment);
        ft.commit();
    }

    @Test
    public void testIsVisible(){
        Activity resultActivity = fragment.getActivity();
        assertThat(resultActivity).isInstanceOf(TestActivity.class);
        assertThat(fragment.isVisible()).isTrue();
    }


}