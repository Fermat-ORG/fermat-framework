package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/10/15.
 */
public interface DigitalAssetSwap {

    void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager);

    void setBitcoinCryptoNetworkManager(BitcoinNetworkManager bitcoinNetworkManager);

    void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws DAPException;

    boolean isDigitalAssetHashValid(DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException, DAPException;

    boolean isAvailableBalanceInAssetVault(long genesisAmount, String genesisTransaction);

    boolean isValidContract(DigitalAssetContract digitalAssetContract);

    void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException;

    void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata);
}
