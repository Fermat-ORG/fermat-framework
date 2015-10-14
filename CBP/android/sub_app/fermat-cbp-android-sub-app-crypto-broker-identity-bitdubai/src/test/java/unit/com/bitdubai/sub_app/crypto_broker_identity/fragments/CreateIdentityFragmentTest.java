package unit.com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.widget.Button;
import android.widget.EditText;

import com.bitdubai.sub_app.intra_user_identity.BuildConfig;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.sub_app.intra_user_identity.fragments.CreateCryptoBrokerIdentityFragment;

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

    private CreateCryptoBrokerIdentityFragment fragment;
    private TestActivity activity;

    @Before
    public void setUp() throws Exception {
        fragment = CreateCryptoBrokerIdentityFragment.newInstance();
        activity = Robolectric.setupActivity(TestActivity.class);

        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.add(TestActivity.LAYOUT_ID, fragment);
        ft.commit();
    }

    @Test
    public void testIsVisible() {
        Activity resultActivity = fragment.getActivity();
        assertThat(resultActivity).isInstanceOf(TestActivity.class);
        assertThat(fragment.isVisible()).isTrue();
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void clickOnCreateButtonSendBackToIdentityListFragment() {
        assertThat(fragment.isVisible()).isTrue();

        Button button = (Button) fragment.getView().findViewById(R.id.create_crypto_broker_button);
        EditText brokerName = (EditText) fragment.getView().findViewById(R.id.crypto_broker_name);
        brokerName.setText("test");

        button.performClick();

        assertThat(fragment.isVisible()).isFalse();
    }
}