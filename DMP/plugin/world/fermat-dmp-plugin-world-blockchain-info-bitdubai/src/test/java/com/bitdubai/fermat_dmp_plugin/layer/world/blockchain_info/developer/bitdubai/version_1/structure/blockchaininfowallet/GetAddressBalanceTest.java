package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.blockchaininfowallet;

import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantGetAddressBalanceException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoWallet;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class GetAddressBalanceTest extends TestCase {

    String apiCode;
    UUID pluginId;
    UUID walletId;

    @Mock ErrorManager errorManager;
    @Mock EventManager eventManager;

    @Mock Wallet mockedWallet;
    @Mock Address mockedAddress;

    BlockchainInfoWallet bcWallet;
    Wallet badWallet;
    CryptoAddress cryptoAddress;

    @Before
    public void setUp() throws Exception {
        bcWallet = new BlockchainInfoWallet(apiCode, errorManager, eventManager, pluginId, walletId);
        badWallet = new Wallet("asd","mjito");
        cryptoAddress = new CryptoAddress();
        cryptoAddress.setAddress("");
        cryptoAddress.setCryptoCurrency(CryptoCurrency.BITCOIN);
    }

    @Test
    public void testGetAddressBalance_NotNull() throws CantGetAddressBalanceException {
        try{ when(mockedWallet.getAddress("",3)).thenReturn(mockedAddress); } catch (Exception e) {}

        bcWallet.setReferenceWallet(mockedWallet);
        long addressBalance = bcWallet.getAddressBalance(cryptoAddress);
        assertNotNull(addressBalance);
    }

    @Test(expected=CantGetAddressBalanceException.class)
    public void testGetAddressBalance_CantGetAddressBalanceException() throws CantGetAddressBalanceException {
        bcWallet.setReferenceWallet(badWallet);
        long addressBalance = bcWallet.getAddressBalance(cryptoAddress);
    }
}