package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_transaction_manager;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.actor_asset_distribution_user.MockActorAssetUser;
import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockDigitalAssetMetadataForTesting;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.AssetDistributionTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 20/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class DistributeAssetsTest {
    private AssetDistributionTransactionManager mockAssetDistributionTransactionManager;
    @Mock
    private ActorAssetIssuerManager actorAssetIssuerManager;
    @Mock
    private AssetVaultManager assetVaultManager;
    @Mock
    private DigitalAssetDistributor digitalAssetDistributor;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private PluginFileSystem pluginFileSystem;
    @Mock
    ActorAssetUser actorAssetUser;
    private HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute;
    @Before
    public void init() throws Exception {
        mockAssetDistributionTransactionManager = new AssetDistributionTransactionManager(assetVaultManager, errorManager, UUID.randomUUID(), pluginDatabaseSystem,
                pluginFileSystem);
        MockDigitalAssetMetadataForTesting mockDigitalAssetMetadata = new MockDigitalAssetMetadataForTesting();
        //ActorAssetUser actorAssetUser = new MockActorAssetUser();
        digitalAssetsToDistribute = new HashMap<DigitalAssetMetadata, ActorAssetUser>();
        digitalAssetsToDistribute.put(mockDigitalAssetMetadata, actorAssetUser);
    }

    @Test
    public void distributeAssetsDigitalAssetsToDistributeNullTest() throws CantDistributeDigitalAssetsException {
        System.out.println("Probando metodo distributeAssetsDigitalAssetsToDistributeNullTest()");
        catchException(mockAssetDistributionTransactionManager).distributeAssets(null, "ASDS-16988807");
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantDistributeDigitalAssetsException.class);
    }

    @Test
    public void distributeAssetsWalletPublicKeyNullTest() throws CantDistributeDigitalAssetsException {
        System.out.println("Probando metodo distributeAssetsWalletPublicKeyNullTest()");
        catchException(mockAssetDistributionTransactionManager).distributeAssets(digitalAssetsToDistribute, null);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantDistributeDigitalAssetsException.class);
    }

    @Test
    public void distributeAssetsWalletTest() throws CantDistributeDigitalAssetsException {
        System.out.println("Probando metodo distributeAssetsWalletTest()");
        catchException(mockAssetDistributionTransactionManager).distributeAssets(digitalAssetsToDistribute, "ASDS-16988807");
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNull();
    }
}
