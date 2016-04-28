package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.ArtistIdentityPluginRootTest;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.ArtistIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by MACUARE on 18/04/16.
 */
public class Teststart {
    private ArtistIdentityPluginRoot artistIdentityPluginRoot;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        artistIdentityPluginRoot = new ArtistIdentityPluginRoot();
        Assert.assertNotNull(artistIdentityPluginRoot);

    }

    @Test
    public void testStartService() throws Exception {
        System.out.println("testStartService");
        artistIdentityPluginRoot.start();
        ServiceStatus actual = artistIdentityPluginRoot.getStatus();
        ServiceStatus expected = ServiceStatus.STARTED;

        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStopService() throws Exception {
        System.out.println("testStopService");
        artistIdentityPluginRoot.start();
        artistIdentityPluginRoot.stop();

        ServiceStatus actual = artistIdentityPluginRoot.getStatus();
        ServiceStatus expected = ServiceStatus.STOPPED;
        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected, actual);
    }

}// end of class
