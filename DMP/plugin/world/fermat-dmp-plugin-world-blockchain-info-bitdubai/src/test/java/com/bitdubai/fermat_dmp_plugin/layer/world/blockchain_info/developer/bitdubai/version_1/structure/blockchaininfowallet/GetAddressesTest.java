package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.blockchaininfowallet;

import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantGetAddressesException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoWallet;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class GetAddressesTest extends TestCase {

    String apiCode;
    UUID pluginId;
    UUID walletId;

    @Mock ErrorManager errorManager;
    @Mock EventManager eventManager;

    @Mock Wallet mockedWallet;

    BlockchainInfoWallet bcWallet;
    Wallet badWallet;

    @Before
    public void setUp() throws Exception {
        bcWallet = new BlockchainInfoWallet(apiCode, errorManager, eventManager, pluginId, walletId);
        badWallet = new Wallet("asd","mjito");
    }

    @Test
    public void testGetAddresses_NotNull() throws CantGetAddressesException {
        bcWallet.setReferenceWallet(mockedWallet);
        List<CryptoAddress> addresses = bcWallet.getAddresses();
        assertNotNull(addresses);
    }

    @Test(expected=CantGetAddressesException.class)
    public void testGetAddresses_CantGetAddressesException() throws CantGetAddressesException {
        bcWallet.setReferenceWallet(badWallet);
        bcWallet.getAddresses();
    }
}