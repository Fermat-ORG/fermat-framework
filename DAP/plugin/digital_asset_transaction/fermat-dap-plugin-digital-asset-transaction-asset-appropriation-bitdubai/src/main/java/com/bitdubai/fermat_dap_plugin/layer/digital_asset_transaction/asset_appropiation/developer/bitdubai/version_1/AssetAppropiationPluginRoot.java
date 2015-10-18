package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropiation.AssetAppropiationManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.DealsWithAssetIssuerWallet;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
public class AssetAppropiationPluginRoot implements Plugin, Service {
    @Override
    public void setId(UUID pluginId) {

    }

    @Override
    public void start() throws CantStartPluginException {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }
}
