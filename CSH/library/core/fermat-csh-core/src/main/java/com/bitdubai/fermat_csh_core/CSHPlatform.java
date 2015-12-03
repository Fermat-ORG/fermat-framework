package com.bitdubai.fermat_csh_core;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.CashMoneyTransactionLayer;
import com.bitdubai.fermat_csh_core.layer.wallet.WalletLayer;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */
public class CSHPlatform extends AbstractPlatform {

    public CSHPlatform() {
        super(new PlatformReference(Platforms.CASH_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {
            registerLayer(new CashMoneyTransactionLayer()   );
            registerLayer(new WalletLayer()                 );
        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}