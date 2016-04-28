package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRootTest.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.structure.FanWalletModuleManagerImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestGetSongsBySongStatus {
    private FanWalletModuleManagerImpl fanWalletModuleManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanWalletModuleManager);
        fanWalletModuleManager = new FanWalletModuleManagerImpl(null, null, null, null, null, null);
        Assert.assertNotNull(fanWalletModuleManager);
    }

    @Test
    public void testGetSongBySongStatusException() throws Exception {
        System.out.println("testGetSongBySongStatusException");
        exception.expect(NullPointerException.class);
        fanWalletModuleManager.getSongsBySongStatus(null);
    }

    @Test
    public void testgetSongBySongStatusContext() throws Exception {
        System.out.println("testgetSongBySongStatusContext");
        exception.expect(NullPointerException.class);
        List<WalletSong> list = new LinkedList<>();
              list  = fanWalletModuleManager.getSongsBySongStatus(SongStatus.NOT_AVAILABLE);
        Assert.assertNull(list);
    }
}//end of class
