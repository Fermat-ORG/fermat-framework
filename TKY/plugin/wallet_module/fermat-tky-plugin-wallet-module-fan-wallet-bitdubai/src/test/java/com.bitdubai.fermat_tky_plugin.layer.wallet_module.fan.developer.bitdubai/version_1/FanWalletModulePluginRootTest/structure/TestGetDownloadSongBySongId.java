package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRootTest.structure;

import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.structure.FanWalletModuleManagerImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;



/**
 * Created by MACUARE on 27/04/16.
 */
public class TestGetDownloadSongBySongId {
    private FanWalletModuleManagerImpl fanWalletModuleManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanWalletModuleManager);
        fanWalletModuleManager = new FanWalletModuleManagerImpl(null, null, null, null,null,null);
        Assert.assertNotNull(fanWalletModuleManager);
    }

    @Test
    public void testGetDownloadSongBySongIdContext() throws Exception {
        System.out.println("testGetDownloadSongBySongIdContext");
        exception.expect(NullPointerException.class);

        DownloadSong downloadSong = fanWalletModuleManager.getDownloadSongBySongId("123456");
        Assert.assertNull(downloadSong);
    }
}//end of class
