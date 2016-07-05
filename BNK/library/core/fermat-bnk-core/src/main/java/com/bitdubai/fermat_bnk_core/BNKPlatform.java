package com.bitdubai.fermat_bnk_core;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_bnk_core.layer.wallet_module.WalletModuleLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_bnk_core.layer.bank_money_transaction.BankMoneyTransactionLayer;
import com.bitdubai.fermat_bnk_core.layer.wallet.WalletLayer;

/**
 * Created by memo on 25/11/15.
 */
public class BNKPlatform extends AbstractPlatform {

    public BNKPlatform() {
        super(new PlatformReference(Platforms.BANKING_PLATFORM));
    }

    public BNKPlatform(FermatContext fermatContext) {
        super(new PlatformReference(Platforms.BANKING_PLATFORM),fermatContext);
    }

    @Override
    public void start() throws CantStartPlatformException {
        try {
            registerLayer(new BankMoneyTransactionLayer());
            registerLayer(new WalletLayer());
            registerLayer(new WalletModuleLayer());
        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
