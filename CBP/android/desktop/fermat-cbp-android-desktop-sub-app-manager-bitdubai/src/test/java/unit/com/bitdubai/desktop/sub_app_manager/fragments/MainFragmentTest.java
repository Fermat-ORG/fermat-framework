package unit.com.bitdubai.desktop.sub_app_manager.fragments;

import android.widget.TextView;

import com.bitdubai.desktop.sub_app_manager.BuildConfig;
import com.bitdubai.desktop.sub_app_manager.R;
import com.bitdubai.desktop.sub_app_manager.fragments.MainFragment;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainFragmentTest {

    private MainFragment fragment;

    @Before
    public void setUp() {
        fragment = MainFragment.newInstance();
    }

    @Ignore
    @Test
    public void fragmentVisible_showHelloFragmentText() {
        final String expectedText = "HELLO FRAGMENT!";

        FragmentTestUtil.startVisibleFragment(fragment);

        TextView helloText = (TextView) fragment.getActivity().findViewById(R.id.helloText);
        String actualText = helloText.getText().toString();

        assertThat(actualText).isEqualTo(expectedText);
    }

}