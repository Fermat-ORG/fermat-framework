package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactory {

    //Datos para el registro del Asset Factory
    UUID getId();
    void setId(UUID id);

    String getNameAssetFactory();
    void setNameAssetFActory(String name);

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

    String getAssetIssuerIdentityPublicKey();
    void setAssetUserIdentityPublicKey(String assetUserIdentityPublicKey);

    DigitalAsset getDigitalAsset();
    void setDigitalAsset(DigitalAsset digitalAsset);

    //Pensando que un factory pudiesemos crear varios Assets, preguntar si esto es posible.
    List<DigitalAsset> getDigitalAssets();
    void setDigitalAssets(List<DigitalAsset> digitalAssets);

    //Datos para el Digital Assets
    String getPublicKey();
    void setPublicKey(String publicKey);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    List<Resource> getResources();
    void setResources(List<Resource> resources);

    DigitalAssetContract getContract();
    void setContract(DigitalAssetContract contract);

    String getGenesisTransaction();
    void setGenesisTransaction(String genesisTransaction);

    CryptoAddress getGenesisAddress();
    void setGenesisAddress(CryptoAddress genesisAddress);

    long getGenesisAmount();
    void setGenesisAmount(long genesisAmount);

    State getState();
    void setState(State state);

    int getQuantity();
    void setQuantity(int quantity);
}
