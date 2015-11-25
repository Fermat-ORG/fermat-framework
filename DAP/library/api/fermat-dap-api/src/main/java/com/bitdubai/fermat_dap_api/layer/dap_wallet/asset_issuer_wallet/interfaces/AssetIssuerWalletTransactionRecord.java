package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

import java.util.UUID;

/**
 * Created by franklin on 05/09/15.
 */
public interface AssetIssuerWalletTransactionRecord {

    //TODO: Definir bien las propiedades
    DigitalAsset getDigitalAsset();
    String getDigitalAssetPublicKey();
    String getName();
    String getDescription();
    String getGenesisBlock();

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

    String getDigitalAssetMetadataHash();

    DigitalAssetMetadata getDigitalAssetMetadata();
}