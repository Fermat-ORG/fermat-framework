package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.ArtistIdentityPluginRootTest;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.ArtistIdentityPluginRoot;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure.ArtistIdentityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by MACUARE on 17/04/16.
 */
public class TestgetFanIdentityManager {
    private ArtistIdentityPluginRoot artistIdentityPluginRoot;
    private ArtistIdentityManager artistIdentityManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(artistIdentityPluginRoot);

        artistIdentityPluginRoot = new ArtistIdentityPluginRoot();
        Assert.assertNotNull(artistIdentityPluginRoot);
        Assert.assertNull(artistIdentityManager);
    }

    @Test
    public void testgetFanIdentityManager() throws Exception{
        System.out.println("testgetFanIdentityManager");
        artistIdentityManager = artistIdentityPluginRoot.getFanIdentityManager();
        Assert.assertNotNull(artistIdentityManager);

        int actual = artistIdentityManager.getMenuNotifications().length;
        int expected = artistIdentityPluginRoot.getModuleManager().getMenuNotifications().length;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testArtistIdentityManagerException() {
        System.out.println("testArtistIdentityManagerException");
        exception.expect(NullPointerException.class);
        int actual = artistIdentityManager.getMenuNotifications()[0];

        Assert.assertNull(actual);
    }

} // end of class
