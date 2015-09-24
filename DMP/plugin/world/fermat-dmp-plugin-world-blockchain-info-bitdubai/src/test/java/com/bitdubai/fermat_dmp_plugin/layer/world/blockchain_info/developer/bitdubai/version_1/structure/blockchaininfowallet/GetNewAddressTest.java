package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.blockchaininfowallet;

import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantGetNewAddressException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoWallet;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetNewAddressTest extends TestCase {

    String apiCode;
    UUID pluginId;
    UUID walletId;

    @Mock ErrorManager errorManager;
    @Mock EventManager eventManager;

    @Mock Wallet mockedWallet;
    @Mock Address mockedAddress;

    BlockchainInfoWallet bcWallet;
    Wallet badWallet;

    @Before
    public void setUp() throws Exception {
        bcWallet = new BlockchainInfoWallet(apiCode, errorManager, eventManager, pluginId, walletId);
        badWallet = new Wallet("asd","mjito");
    }

    @Test
    public void testGetNewAddress_NotNull() throws CantGetNewAddressException {
        try{ when(mockedWallet.newAddress(null)).thenReturn(mockedAddress); } catch (Exception e) {}
        bcWallet.setReferenceWallet(mockedWallet);
        CryptoAddress address = bcWallet.getNewAddress();
        assertNotNull(address);
    }

    @Test(expected=CantGetNewAddressException.class)
    public void testGetNewAddress_CantGetNewAddressException() throws CantGetNewAddressException {
        bcWallet.setReferenceWallet(badWallet);
        bcWallet.getNewAddress();
    }
}