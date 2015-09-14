package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactory {

    UUID getId();
    void setId(UUID id);

    String getName();
    void setName(String name);

    WalletCategory getWalletCategory();
    void setWalletCategory(WalletCategory walletCategory);

    WalletType getWalletType();
    void setWalletType(WalletType walletType);

    Timestamp getCreationTimestamp();
    void setCreationTimestamp(Timestamp timestamp);

    Timestamp getLastModificationTimestamp();
    void setLastModificationTimeststamp(Timestamp timestamp);

    String getWalletPublicKey();
    void setWalletPublicKey(String walletPublicKey);

    String getAssetUserIdentityPublicKey();
    void setAssetUserIdentityPublicKey(String assetUserIdentityPublicKey);

    DigitalAsset getDigitalAsset();
    void setDigitalAsset(DigitalAsset digitalAsset);

    //Pensando que un factory pudiesemos crear varios Assets, preguntar si esto es posible.
    List<DigitalAsset> getDigitalAssets();
    void setDigitalAssets(List<DigitalAsset> digitalAssets);

//    String getPublicKey();
//    void setPublicKey(String publicKey);
//
//    String getName();
//    void setName(String name);
//
//    String getDescription();
//    void setDescription(String description);
//
//    List<Resource> getResources();
//    void setResources(List<Resource> resources);
//
//    DigitalAssetContract getContract();
//    void setContract(DigitalAssetContract contract);
//
//    String getGenesisTransaction();
//    void setGenesisTransaction(String genesisTransaction);
//
//    CryptoAddress getGenesisAddress();
//    void setGenesisAddress(CryptoAddress genesisAddress);
//
//    long getGenesisAmount();
//    void setGenesisAmount(long genesisAmount);
//
//    State getState();
//    void setState(State state);
//
//    int getQuantity();
//    void setQuantity(int quantity);
//
//    long getTransactionFee();
//    void setTransactionFee(long transactionFee);
//
//    long getUnitValue();
//    void setUnitValue(long unitValue);
}
