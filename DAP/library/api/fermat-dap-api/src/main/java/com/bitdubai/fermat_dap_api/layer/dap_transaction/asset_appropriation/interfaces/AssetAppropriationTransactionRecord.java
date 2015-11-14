package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public interface AssetAppropriationTransactionRecord {

    String transactionRecordId();
    
    String genesisTransaction();
    
    AppropriationStatus status();

    DigitalAsset digitalAsset();

    String btcWalletPublicKey();

    CryptoAddress addressTo();

    String userWalletPublicKey();

    Date startTime();

    Date endTime();
}
