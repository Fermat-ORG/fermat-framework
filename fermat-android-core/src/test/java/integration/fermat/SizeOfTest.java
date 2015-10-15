package integration.fermat;

import android.app.Activity;
import android.content.Context;

import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsDataBaseSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsFileSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsLocationSystem;
import com.bitdubai.fermat.BuildConfig;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.osa_android.LoggerSystemOs;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.LoggerAddonRoot;
import com.carrotsearch.sizeof.RamUsageEstimator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jorge on 15-10-2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SizeOfTest {

    private AndroidOsFileSystem fileSystemOs;
    private CorePlatformContext platformContext;
    private AndroidOsDataBaseSystem databaseSystemOs;
    private AndroidOsLocationSystem locationSystemOs;
    private LoggerSystemOs loggerSystemOs;

    private Platform platform;

    private Activity mockActivity;
    private Context mockContext;

    @Before
    public void setUp() throws Exception{
        setUpContext();
        setUpDatabaseSystem();
        setUpFileSystem();
        setUpLocationSystem();
        setUpLoggerSystem();
        setUpPlatform();
    }

    private void setUpContext() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();
    }

    private void setUpFileSystem() throws Exception {
        fileSystemOs = new AndroidOsFileSystem();
        fileSystemOs.setContext(mockContext);
    }

    private void setUpDatabaseSystem() throws Exception {
        databaseSystemOs = new AndroidOsDataBaseSystem();
        databaseSystemOs.setContext(mockContext);
    }

    private void setUpLocationSystem() throws Exception {
        locationSystemOs = new AndroidOsLocationSystem();
        locationSystemOs.setContext(mockContext);
    }

    private void setUpLoggerSystem() throws Exception {
        loggerSystemOs =  new LoggerAddonRoot();
        ((Service) loggerSystemOs).start();
    }

    public void setUpPlatform(){

        platform = new Platform();
        platform.setDataBaseSystemOs(databaseSystemOs);
        platform.setFileSystemOs(fileSystemOs);
        platform.setLocationSystemOs(locationSystemOs);
        platform.setLoggerSystemOs(loggerSystemOs);
    }

    @Test
    public void Platform_Instantiated_ShouldBeHuge() throws Exception{
        platform.start();
        long size = RamUsageEstimator.sizeOf(platform);
        System.out.println(size);
        assertThat(size).isGreaterThan(0L);
    }
}
