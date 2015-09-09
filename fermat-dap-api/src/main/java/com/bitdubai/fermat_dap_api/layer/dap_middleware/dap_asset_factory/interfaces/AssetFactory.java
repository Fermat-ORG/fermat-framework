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

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactory {

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

    long getTransactionFee();
    void setTransactionFee(long transactionFee);

    long getUnitValue();
    void setUnitValue(long unitValue);
}
