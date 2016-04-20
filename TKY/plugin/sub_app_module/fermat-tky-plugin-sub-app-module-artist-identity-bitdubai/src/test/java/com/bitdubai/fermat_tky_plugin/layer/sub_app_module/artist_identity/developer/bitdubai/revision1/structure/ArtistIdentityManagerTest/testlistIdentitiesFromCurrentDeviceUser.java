package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.revision1.structure.ArtistIdentityManagerTest;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
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
public class testlistIdentitiesFromCurrentDeviceUser {
    private ArtistIdentityManager artistIdentityManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Assert.assertNull(artistIdentityManager);
        artistIdentityManager = new ArtistIdentityManager(null,null);
        Assert.assertNotNull(artistIdentityManager);

    }

    @Test
    public void testlistIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        exception.expect(NullPointerException.class);

        List<Artist> artists = artistIdentityManager.listIdentitiesFromCurrentDeviceUser();
        Assert.assertNull(artists);
        Assert.assertTrue(artists.isEmpty());


    }
}// end of class
