/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.WalletModuleCryptoWallet;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
import ErrorManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantInitializeCryptoWalletManagerException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static com.googlecode.catchexception.CatchException.*;

@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase {

    */
/**
     * DealsWithBitcoinWallet interface Mocked
     *//*

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    */
/**
     * DealsWithCryptoVault interface Mocked
     *//*

    @Mock
    CryptoVaultManager cryptoVaultManager;

    */
/**
     * DealsWithErrors interface Mocked
     *//*

    @Mock
    ErrorManager errorManager;

    */
/**
     * DealsWithExtraUsers interface Mocked
     *//*

    @Mock
    ExtraUserManager extraUserManager;

    */
/**
     * DealsWithOutgoingExtraUser interface Mocked
     *//*

    @Mock
    OutgoingExtraUserManager outgoingExtraUserManager;

    */
/**
     * DealsWithCryptoAddressBook interface Mocked
     *//*

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    */
/**
     * DealsWithWalletContacts interface Mocked
     *//*

    @Mock
    WalletContactsManager walletContactsManager;


    CryptoWalletWalletModuleManager walletModuleCryptoWallet;

    @Before
    public void setUp() throws Exception {
        walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();
        walletModuleCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
        walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        walletModuleCryptoWallet.setErrorManager(errorManager);
        walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
        walletModuleCryptoWallet.setOutgoingExtraUserManager(outgoingExtraUserManager);
        walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
        walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
    }

    @Test
    public void testGetCryptoWallet_Success() throws Exception {
        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException()).isNull();
    }

    @Test
    public void testInitialize_CantGetWalletContactRegistryException() throws Exception {
        doThrow(new CantGetWalletContactRegistryException()).when(walletContactsManager).getWalletContactsRegistry();

        catchException(walletModuleCryptoWallet).initialize();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeCryptoWalletManagerException.class);
    }
}
*/
