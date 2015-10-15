package integration.com.bitdubai.fermat_core.Platform;

import com.bitdubai.fermat_core.Platform;
import com.carrotsearch.sizeof.RamUsageEstimator;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorge on 15-10-2015.
 */
public class SizeOfTest {

    private Platform platform;

    @Before
    public void setPlatform(){
        platform = new Platform();
    }

    @Test
    public void Platform_Instantiated_ShouldBeHuge() throws Exception{
        platform.start();
        long size = RamUsageEstimator.sizeOf(platform);
        System.out.println(size);
        assertThat(size).isGreaterThan(0L);
    }
}
