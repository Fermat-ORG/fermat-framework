package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure.ArtistIdentityManagerTest;

import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure.ArtistIdentityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

/**
 * Created by MACUARE on 19/04/16.
 */
public class TestListIdentitiesFromCurrentDeviceUser {
    private ArtistIdentityManager artistIdentityManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(artistIdentityManager);
        artistIdentityManager = new ArtistIdentityManager(null,null,null, pluginFileSystem, pluginId);
        Assert.assertNotNull(artistIdentityManager);

    }

    @Test
    public void testListIdentitiesFromCurrentDeviceUser() throws Exception {
        System.out.println("TestListIdentitiesFromCurrentDeviceUser");
        exception.expect(NullPointerException.class);

        List<Artist> artists = artistIdentityManager.listIdentitiesFromCurrentDeviceUser();
        Assert.assertNull(artists);
        Assert.assertTrue(artists.isEmpty());

    }

}// end of class
