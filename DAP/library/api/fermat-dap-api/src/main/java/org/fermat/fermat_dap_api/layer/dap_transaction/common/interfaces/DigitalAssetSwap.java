package org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;


import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/10/15.
 */
public interface DigitalAssetSwap {

    void setAssetTransmissionNetworkServiceManager(org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager);

    void setBitcoinCryptoNetworkManager(BlockchainManager<ECKey, Transaction> bitcoinNetworkManager);

    void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

    boolean isDigitalAssetHashValid(DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException, org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

    boolean isAvailableBalanceInAssetVault(long genesisAmount, String genesisTransaction);

    boolean isValidContract(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract digitalAssetContract);

    void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException;

    void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata);
}
