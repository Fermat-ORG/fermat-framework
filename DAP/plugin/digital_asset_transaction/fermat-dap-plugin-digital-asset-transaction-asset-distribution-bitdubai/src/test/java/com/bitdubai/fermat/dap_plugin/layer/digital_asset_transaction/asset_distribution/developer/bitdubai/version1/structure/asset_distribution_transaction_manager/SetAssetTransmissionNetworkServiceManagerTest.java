package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_transaction_manager;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.AssetDistributionTransactionManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.fest.assertions.api.Assertions.assertThat;
import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

/**
 * Created by luis on 20/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetAssetTransmissionNetworkServiceManagerTest {
    @Mock
    private AssetTransmissionNetworkServiceManager mockAssetTransmissionNetworkServiceManager;
    @Mock
    private AssetDistributionTransactionManager mockAssetDistributionTransactionManager;

    @Test
    public void setAssetTransmissionNetworkServiceManager_ThrowsCantSetObjectException() throws Exception {
        mockAssetDistributionTransactionManager.setAssetTransmissionNetworkServiceManager(null);
    }


}

    /*@Test
    public void GetBookBalance_OpenDatabaseCantOpenDatabaseException_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new CantOpenDatabaseException("MOCK", null, null, null)).when(mockDatabase).openDatabase();
        catchException(testWalletDao).getBookBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }*/
    /*public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) throws CantSetObjectException {
        if(assetTransmissionNetworkServiceManager==null){
            throw new CantSetObjectException("assetTransmissionNetworkServiceManager is null");
        }
        this.digitalAssetDistributor.setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
    }*/