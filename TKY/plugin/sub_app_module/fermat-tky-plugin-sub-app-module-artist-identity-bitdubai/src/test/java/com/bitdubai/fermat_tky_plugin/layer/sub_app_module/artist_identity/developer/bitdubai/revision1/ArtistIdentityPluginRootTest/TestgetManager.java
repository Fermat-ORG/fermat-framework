package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.revision1.ArtistIdentityPluginRootTest;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;
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


    @Test
    public void testManagerInstance() throws CantStartPluginException, TKYException {
        System.out.println("testManagerInstance");
        artistIdentityPluginRoot.start();
        ModuleManager expected = artistIdentityPluginRoot.getFanIdentityManager();
        ModuleManager actual = artistIdentityPluginRoot.getManager();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testManagerNull() {
        System.out.println("testManagerNull");
        ModuleManager moduleManager = artistIdentityPluginRoot.getManager();
        Assert.assertNull(moduleManager);
    }


    @Test
    public void testManagerNotNull() throws CantStartPluginException {
        System.out.println("testManagerNotNull");
        artistIdentityPluginRoot.start();
        ModuleManager moduleManager = artistIdentityPluginRoot.getManager();
        Assert.assertNotNull(moduleManager);
    }

}//end of class
