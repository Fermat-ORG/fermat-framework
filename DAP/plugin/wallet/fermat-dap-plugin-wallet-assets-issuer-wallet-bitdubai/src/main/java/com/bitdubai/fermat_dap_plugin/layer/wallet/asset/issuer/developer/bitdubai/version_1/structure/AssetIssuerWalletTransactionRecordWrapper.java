package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;

import java.util.UUID;

/**
 * Created by franklin on 01/10/15.
 */
public class AssetIssuerWalletTransactionRecordWrapper implements AssetIssuerWalletTransactionRecord {
    @Override
    public DigitalAsset getDigitalAsset() {
        return null;
    }

    @Override
    public String getAssetIssuingPublicKey() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return null;
    }

    @Override
    public String getActorFromPublicKey() {
        return null;
    }

    @Override
    public Actors getActorFromType() {
        return null;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return null;
    }

    @Override
    public String getActorToPublicKey() {
        return null;
    }

    @Override
    public Actors getActorToType() {
        return null;
    }

    @Override
    public UUID getIdTransaction() {
        return null;
    }

    @Override
    public long getAmount() {
        return 0;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public String getMemo() {
        return null;
    }

    @Override
    public String getDigitalAssetMetadataHash() {
        return null;
    }
}
