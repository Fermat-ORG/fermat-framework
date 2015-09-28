package com.bitdubai.desktop.wallet_manager.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.widget.TextView;


import com.bitdubai.desktop.wallet_manager.BuildConfig;
import com.bitdubai.desktop.wallet_manager.R;
import com.bitdubai.desktop.wallet_manager.TestActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainFragmentTest {

    private MainFragment fragment;
    private TestActivity activity;

    @Before
    public void setUp() {
        fragment = MainFragment.newInstance();

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

        TextView helloText = (TextView) fragment.getView().findViewById(R.id.helloText);
        assertThat(helloText).isNotNull();
    }

    @Test
    public void helloTextViewShowCorrectText() {
        final String expectedText = "Hello blank fragment";

        TextView helloText = (TextView) fragment.getView().findViewById(R.id.helloText);
        String actualText = helloText.getText().toString();
        assertThat(actualText).isEqualTo(expectedText);
    }


}