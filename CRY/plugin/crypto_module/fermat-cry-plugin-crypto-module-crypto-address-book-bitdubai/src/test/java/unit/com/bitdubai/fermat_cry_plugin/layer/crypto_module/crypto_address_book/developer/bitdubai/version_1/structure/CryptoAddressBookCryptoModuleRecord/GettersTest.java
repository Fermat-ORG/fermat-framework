package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure.CryptoAddressBookCryptoModuleRecord;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure.CryptoAddressBookCryptoModuleRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 07/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GettersTest {



private CryptoAddressBookCryptoModuleRecord cryptoAddressBookCryptoModuleRecord;

    private   CryptoAddress cryptoAddress = new CryptoAddress();

    private  String deliveredByActorPublicKey = UUID.randomUUID().toString();

    private Actors deliveredByActorType = Actors.EXTRA_USER;

    private  String deliveredToActorPublicKey = UUID.randomUUID().toString();;

    private  Actors deliveredToActorType = Actors.INTRA_USER;

    private Platforms platform = Platforms.CRYPTO_CURRENCY_PLATFORM;

    private CryptoCurrencyVault vault = CryptoCurrencyVault.BITCOIN_VAULT;

    private String walletPublicKey = UUID.randomUUID().toString();;

    private ReferenceWallet walletType = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;

        @Before
        public void setUp() throws Exception{

          cryptoAddressBookCryptoModuleRecord = new CryptoAddressBookCryptoModuleRecord(cryptoAddress,
                   deliveredByActorPublicKey,
                   deliveredByActorType,
                   deliveredToActorPublicKey,
                   deliveredToActorType,
                   platform,
                   vault,
                   walletPublicKey,
                   walletType);


        }
        @Test
        public void getCryptoAddressTest_AreEquals(){

            assertThat(cryptoAddressBookCryptoModuleRecord.getCryptoAddress()).isEqualTo(cryptoAddress);
        }

        @Test
        public void getDeliveredByActorPublicKeyTest_AreEquals(){
            assertThat(cryptoAddressBookCryptoModuleRecord.getDeliveredByActorPublicKey()).isEqualTo(deliveredByActorPublicKey);
        }

        @Test
        public void getDeliveredByActorTypeTest_AreEquals() throws CantShowProfileImageException {
            assertThat(cryptoAddressBookCryptoModuleRecord.getDeliveredByActorType()).isEqualTo(deliveredByActorType);
        }

    @Test
    public void getDeliveredToActorPublicKeyTest_AreEquals() throws CantShowProfileImageException {
        assertThat(cryptoAddressBookCryptoModuleRecord.getDeliveredToActorPublicKey()).isEqualTo(deliveredToActorPublicKey);
    }

    @Test
    public void getDeliveredToActorTypeTest_AreEquals() throws CantShowProfileImageException {
        assertThat(cryptoAddressBookCryptoModuleRecord.getDeliveredToActorType()).isEqualTo(deliveredToActorType);
    }

    @Test
    public void getPlatformTest_AreEquals() throws CantShowProfileImageException {
        assertThat(cryptoAddressBookCryptoModuleRecord.getPlatform()).isEqualTo(platform);
    }

    @Test
    public void getVaultTypeTest_AreEquals() throws CantShowProfileImageException {
        assertThat(cryptoAddressBookCryptoModuleRecord.getVault()).isEqualTo(vault);
    }

    @Test
    public void getWalletTypeTest_AreEquals() throws CantShowProfileImageException {
        assertThat(cryptoAddressBookCryptoModuleRecord.getWalletType()).isEqualTo(walletType);
    }

    @Test
    public void getWalletPublicKeyTest_AreEquals() throws CantShowProfileImageException {
        assertThat(cryptoAddressBookCryptoModuleRecord.getWalletPublicKey()).isEqualTo(walletPublicKey);
    }
}
