package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactory {
    String getWalletPublicKey();
    void setWalletPublicKey(String walletPublicKey);

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

//    ContractProperty getContractProperty();
//    void setContractProperty(ContractProperty contractProperty);

    List<ContractProperty> getContractProperties();
    void setContractProperties(List<ContractProperty> contractProperties);

    State getState();
    void setState(State state);

    int getQuantity();
    void setQuantity(int quantity);

    long getAmount();
    void setAmount(long amount);

    long getFee();
    void setFee(long fee);

    boolean getIsRedeemable();
    void setIsRedeemable(boolean isRedeemable);

    Timestamp getExpirationDate();
    void setExpirationDate(Timestamp expirationDate);

    AssetBehavior getAssetBehavior();
    void setAssetBehavior(AssetBehavior assetBehavior);

    Timestamp getCreationTimestamp();
    void setCreationTimestamp(Timestamp timestamp);

    Timestamp getLastModificationTimestamp();
    void setLastModificationTimeststamp(Timestamp timestamp);

//    String getAssetIssuerIdentityPublicKey();
//    void setAssetUserIdentityPublicKey(String assetUserIdentityPublicKey);

    IdentityAssetIssuer getIdentyAssetIssuer();
    void setIdentityAssetIssuer(IdentityAssetIssuer identityAssetIssuer);
}
