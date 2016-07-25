package unit.com.bitdubai.sub_app.crypto_customer_identity.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;

import com.bitdubai.sub_app.crypto_customer_identity.BuildConfig;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.CryptoCustomerIdentityListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import unit.com.bitdubai.sub_app.crypto_customer_identity.TestActivity;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CryptoCustomerIdentityListFragmentTest {

    private CryptoCustomerIdentityListFragment fragment;
    private TestActivity activity;

    @Before
    public void setUp() {
        fragment = CryptoCustomerIdentityListFragment.newInstance();

        activity = Robolectric.setupActivity(TestActivity.class);

        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.add(fragment, null);
        ft.commit();
    }

    @Test
    public void fragmentIsVisibleInActivity() {
        Activity resultActivity = fragment.getActivity();
        assertThat(resultActivity).isInstanceOf(TestActivity.class);
    }

    @Test
    public void helloTextViewIsNotNull() {
//        TextView helloText = (TextView) fragment.getView().findViewById(R.id.helloText);
//        assertThat(helloText).isNotNull();
    }

    @Test
    public void helloTextViewShowCorrectText() {
//        final String expectedText = "Hello blank fragment";
//
//        TextView helloText = (TextView) fragment.getView().findViewById(R.id.helloText);
//        String actualText = helloText.getText().toString();
//        assertThat(actualText).isEqualTo(expectedText);
    }


}