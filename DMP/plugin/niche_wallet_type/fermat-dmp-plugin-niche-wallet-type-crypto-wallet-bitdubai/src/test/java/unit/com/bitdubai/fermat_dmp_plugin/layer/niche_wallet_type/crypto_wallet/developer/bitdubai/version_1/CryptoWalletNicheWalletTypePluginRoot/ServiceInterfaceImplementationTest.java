package unit.com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ServiceInterfaceImplementationTest extends TestCase {

    /**
     * DealsWithActorAddressBook interface Mocked
     */
    @Mock
    ActorAddressBookManager actorAddressBookManager;

    /**
     * DealsWithBitcoinWallet interface Mocked
     */
    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithCryptoVault interface Mocked
     */
    @Mock
    CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

    /**
     * DealsWithExtraUsers interface Mocked
     */
    @Mock
    ExtraUserManager extraUserManager;

    /**
     * DealsWithOutgoingExtraUser interface Mocked
     */
    @Mock
    OutgoingExtraUserManager outgoingExtraUserManager;

    /**
     * DealsWithWalletAddressBook interface Mocked
     */
    @Mock
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;


    CryptoWalletNicheWalletTypePluginRoot cryptoWalletNicheWalletTypePluginRoot;

    @Before
    public void setUp() throws Exception {
        cryptoWalletNicheWalletTypePluginRoot = new CryptoWalletNicheWalletTypePluginRoot();
        cryptoWalletNicheWalletTypePluginRoot.setActorAddressBookManager(actorAddressBookManager);
        cryptoWalletNicheWalletTypePluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        cryptoWalletNicheWalletTypePluginRoot.setCryptoVaultManager(cryptoVaultManager);
        cryptoWalletNicheWalletTypePluginRoot.setErrorManager(errorManager);
        cryptoWalletNicheWalletTypePluginRoot.setExtraUserManager(extraUserManager);
        cryptoWalletNicheWalletTypePluginRoot.setOutgoingExtraUserManager(outgoingExtraUserManager);
        cryptoWalletNicheWalletTypePluginRoot.setWalletAddressBookManager(walletAddressBookManager);
        cryptoWalletNicheWalletTypePluginRoot.setWalletContactsManager(walletContactsManager);
    }

    @Test
    public void testSetId() throws Exception {
        cryptoWalletNicheWalletTypePluginRoot.setId(null);
    }
}
