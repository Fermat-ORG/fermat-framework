package unit.com.bitdubai.desktop.sub_app_manager.CbpSubAppListGenerator;

import android.app.Fragment;

import com.bitdubai.desktop.sub_app_manager.BuildConfig;
import com.bitdubai.desktop.sub_app_manager.SubAppDesktopFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 22/09/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SubAppDesktopFragmentTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void newInstanceMethodReturnFragment(){
        final int position = 0;
        assertThat(SubAppDesktopFragment.newInstance(position)).isInstanceOf(Fragment.class);
    }


}