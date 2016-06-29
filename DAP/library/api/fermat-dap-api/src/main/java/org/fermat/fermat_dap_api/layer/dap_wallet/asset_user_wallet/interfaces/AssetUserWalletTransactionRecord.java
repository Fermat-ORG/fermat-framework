package org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletTransactionRecord {
    //TODO: Definir bien las propiedades
    org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset getDigitalAsset();

    String getAssetIssuingPublicKey();

    String getName();

    String getDescription();

    CryptoAddress getAddressFrom();

    String getActorFromPublicKey();

    Actors getActorFromType();

    CryptoAddress getAddressTo();

    String getActorToPublicKey();

    Actors getActorToType();

    String getIdTransaction();

    long getAmount();

    long getTimestamp();

    String getMemo();

    String getGenesisTransaction();

    DigitalAssetMetadata getDigitalAssetMetadata();
}
