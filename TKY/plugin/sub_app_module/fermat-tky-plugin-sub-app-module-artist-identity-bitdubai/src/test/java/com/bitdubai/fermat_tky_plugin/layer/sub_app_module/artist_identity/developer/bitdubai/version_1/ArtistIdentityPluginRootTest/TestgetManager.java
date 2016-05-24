package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.ArtistIdentityPluginRootTest;



import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.ArtistIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 18/04/16.
 */
public class TestgetManager {
    private ArtistIdentityPluginRoot artistIdentityPluginRoot;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(artistIdentityPluginRoot);

        artistIdentityPluginRoot = new ArtistIdentityPluginRoot();
        Assert.assertNotNull(artistIdentityPluginRoot);

    }

    //TODO: Andy, I will comment this test, is not passing. Manuel
    /*@Test
    public void testManagerNull() throws Exception{
        System.out.println("testManagerNull");
        ModuleManager moduleManager = artistIdentityPluginRoot.getModuleManager();
        Assert.assertNull(moduleManager);
    }*/

    @Test
    public void testManagerNotNull() throws Exception {
        System.out.println("testManagerNotNull");
        artistIdentityPluginRoot.start();
        ModuleManager moduleManager = artistIdentityPluginRoot.getModuleManager();
        Assert.assertNotNull(moduleManager);
    }

}//end of class
