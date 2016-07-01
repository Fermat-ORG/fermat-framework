package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure.ArtistIdentityManagerTest;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure.ArtistIdentityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by MACUARE on 19/04/16.
 */
public class TestCreateArtistIdentity {

    private ArtistIdentityManager artistIdentityManager;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(artistIdentityManager);
        artistIdentityManager = new ArtistIdentityManager(null, null,null, pluginFileSystem, pluginId);
        Assert.assertNotNull(artistIdentityManager);
    }

    @Test
    public void testcreateArtistIdentity() throws Exception {
        System.out.println("TestCreateArtistIdentity");
        exception.expect(NullPointerException.class);
        Artist artist =  artistIdentityManager.createArtistIdentity(
                "jon doe",
                "image".getBytes(),
                "123456",
                ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM,
                ExposureLevel.DEFAULT_EXPOSURE_LEVEL,
                ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE);

        Assert.assertNull(artist);
    }

}// end of class
