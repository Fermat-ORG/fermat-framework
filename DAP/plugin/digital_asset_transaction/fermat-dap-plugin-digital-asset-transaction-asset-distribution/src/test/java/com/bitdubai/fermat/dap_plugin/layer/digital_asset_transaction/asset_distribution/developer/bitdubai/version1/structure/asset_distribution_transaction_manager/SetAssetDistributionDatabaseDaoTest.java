package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_transaction_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.AssetDistributionTransactionManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 20/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetAssetDistributionDatabaseDaoTest {
    private AssetDistributionDao assetDistributionDatabaseDao = Mockito.mock(AssetDistributionDao.class);
    private AssetDistributionTransactionManager mockAssetDistributionTransactionManager;
    @Mock
    private AssetVaultManager assetVaultManager;
    @Mock
    private DigitalAssetDistributor digitalAssetDistributor;
    @Mock
    private ErrorManager errorManager;
    UUID pluginId;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private PluginFileSystem pluginFileSystem;
    private Database mockDatabase = Mockito.mock(Database.class);

    @Before
    public void init() throws Exception {
        mockAssetDistributionTransactionManager = new AssetDistributionTransactionManager(assetVaultManager, errorManager, UUID.randomUUID(), pluginDatabaseSystem,
                pluginFileSystem);
        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenReturn(mockDatabase);
    }

    @Test
    public void setAssetDistributionDatabaseDaoThrowsCantSetObjectExceptionTest() throws CantSetObjectException {
        System.out.println("Probando metodo setAssetDistributionDatabaseDaoThrowsCantSetObjectExceptionTest()");
        try {
            mockAssetDistributionTransactionManager.setAssetDistributionDatabaseDao(null);
            fail("The method didn't throw when I expected it to");
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof CantSetObjectException);
        }
    }

    @Test
    public void setAssetDistributionDatabaseDaoNoExceptionTest() throws CantSetObjectException {
        System.out.println("Probando metodo setAssetDistributionDatabaseDaoNoExceptionTest()");
        mockAssetDistributionTransactionManager.setAssetDistributionDatabaseDao(assetDistributionDatabaseDao);
    }
}
