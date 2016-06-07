package org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public interface AppropriationTransactionRecord {

    String transactionRecordId();

    BlockchainNetworkType networkType();

    String genesisTransaction();

    org.fermat.fermat_dap_api.layer.all_definition.enums.AppropriationStatus status();

    org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset();

    DigitalAssetMetadata assetMetadata();

    String btcWalletPublicKey();

    CryptoAddress addressTo();

    String walletPublicKey();

    Date startTime();

    Date endTime();
}
