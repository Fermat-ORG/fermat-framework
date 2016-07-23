package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker_wallet.CryptoBrokerWalletModuleCryptoBrokerWalletManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerIdentity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * Created by nelsonalfo on 13/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetMerchandisesAsExtraDataInAssociatedIdentityTest extends ParentTestClass {
    final String BROKER_IDENTITY_PUBLIC_KEY = "broker_public_key";
    final String WALLET_PUBLIC_KEY = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

    private CryptoBrokerWalletModuleCryptoBrokerIdentity baseIdentity;


    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        baseIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(
                BROKER_IDENTITY_PUBLIC_KEY, "testAlias", new byte[0],
                ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10);

        doReturn(baseIdentity).when(moduleManagerSpy).getAssociatedIdentity(WALLET_PUBLIC_KEY);
    }

    @Test
    public void updateExtraDataWithThreeWalletsAndThreeDifferentMerchandises() throws Exception {

        // setup
        List<CryptoBrokerWalletAssociatedSetting> associatedWallets;
        associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.ARGENTINE_PESO));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        CryptoBrokerIdentity expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(
                BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias",
                new byte[0],
                ExposureLevel.PUBLISH,
                GeoFrequency.NORMAL,
                10,
                CryptoCurrency.BITCOIN,
                FiatCurrency.US_DOLLAR,
                "Merchandises: BTC, USD, ARS");

        CryptoBrokerIdentityExtraData expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        CryptoBrokerIdentityExtraData updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));
        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());
    }

    @Test
    public void updateExtraDataWithThreeWalletsAndTwoDifferentMerchandises() throws Exception {

        // setup
        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        final CryptoBrokerIdentity expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(
                BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias",
                new byte[0],
                ExposureLevel.PUBLISH,
                GeoFrequency.NORMAL,
                10,
                CryptoCurrency.BITCOIN,
                FiatCurrency.US_DOLLAR,
                "Merchandises: BTC, USD");

        CryptoBrokerIdentityExtraData expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        CryptoBrokerIdentityExtraData updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));
        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());
    }

    @Test
    public void updateExtraDataWithTwoWalletsAndTwoDifferentMerchandises() throws Exception {

        // setup
        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        final CryptoBrokerIdentity expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(
                BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias", new byte[0],
                ExposureLevel.PUBLISH,
                GeoFrequency.NORMAL,
                10,
                CryptoCurrency.BITCOIN,
                FiatCurrency.US_DOLLAR,
                "Merchandises: BTC, USD");

        CryptoBrokerIdentityExtraData expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        CryptoBrokerIdentityExtraData updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));
    }

    @Test
    public void doNotUpdateExtraDataWhenThereIsNoWalletsAndMerchandises() throws Exception {
        // setup
        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        assertThat(updatedIdentity.getAccuracy()).isEqualTo(baseIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(baseIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(baseIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(baseIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(baseIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(baseIdentity.getPublicKey());
        assertThat(updatedIdentity.getCryptoBrokerIdentityExtraData()).isNull();

        Mockito.verify(cryptoBrokerIdentityManager, never()).updateCryptoBrokerIdentity(eq(updatedIdentity));
    }
}
