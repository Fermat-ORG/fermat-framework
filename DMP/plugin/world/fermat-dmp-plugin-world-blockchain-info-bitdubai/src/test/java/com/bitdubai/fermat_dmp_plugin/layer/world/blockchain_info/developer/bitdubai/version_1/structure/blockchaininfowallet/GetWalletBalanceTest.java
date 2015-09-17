package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.blockchaininfowallet;

import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantGetWalletBalanceException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class GetWalletBalanceTest extends TestCase {

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
    public void testGetWalletBalance_NotNull() throws CantGetWalletBalanceException {
        bcWallet.setReferenceWallet(mockedWallet);
        long walletBalance = bcWallet.getWalletBalance(CryptoCurrency.BITCOIN);
        assertNotNull(walletBalance);
    }

    @Test(expected=CantGetWalletBalanceException.class)
    public void testGetWalletBalance_CantGetWalletBalanceException() throws CantGetWalletBalanceException {
        bcWallet.setReferenceWallet(badWallet);
        long walletBalance = bcWallet.getWalletBalance(CryptoCurrency.BITCOIN);
    }
}