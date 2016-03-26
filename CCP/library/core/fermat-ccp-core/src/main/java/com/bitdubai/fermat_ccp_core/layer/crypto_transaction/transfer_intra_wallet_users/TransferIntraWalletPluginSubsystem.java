package com.bitdubai.fermat_ccp_core.layer.crypto_transaction.transfer_intra_wallet_users;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.DeveloperBitDubai;

/**
 * Created by natalia on 24/03/16.
 */
public class TransferIntraWalletPluginSubsystem extends AbstractPluginSubsystem {

    public TransferIntraWalletPluginSubsystem() {
        super(new PluginReference(Plugins.TRANSFER_INTRA_WALLET));
    }
    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }

}