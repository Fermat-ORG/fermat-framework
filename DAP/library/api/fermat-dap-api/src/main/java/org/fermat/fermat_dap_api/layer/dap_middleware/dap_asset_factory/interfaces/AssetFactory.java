package org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactory extends Serializable {
    String getFactoryId();
    void setFactoryId(String factoryId);

    String getWalletPublicKey();
    void setWalletPublicKey(String walletPublicKey);

    String getAssetPublicKey();
    void setPublicKey(String publicKey);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    List<Resource> getResources();
    void setResources(List<Resource> resources);

    org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract getContract();
    void setContract(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract contract);

    List<ContractProperty> getContractProperties();
    void setContractProperties(List<ContractProperty> contractProperties);

    State getState();
    void setState(State state);

    int getQuantity();
    void setQuantity(int quantity);

    int getTotalQuantity();
    void setTotalQuantity(int quantity);

    long getAmount();
    void setAmount(long amount);

    long getFee();
    void setFee(long fee);

    boolean getIsRedeemable();
    void setIsRedeemable(boolean isRedeemable);

    Timestamp getExpirationDate();
    void setExpirationDate(Timestamp expirationDate);

    org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior getAssetBehavior();
    void setAssetBehavior(org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior assetBehavior);

    Timestamp getCreationTimestamp();
    void setCreationTimestamp(Timestamp timestamp);

    Timestamp getLastModificationTimestamp();
    void setLastModificationTimeststamp(Timestamp timestamp);

    org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer getIdentyAssetIssuer();
    void setIdentityAssetIssuer(org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer identityAssetIssuer);

    BlockchainNetworkType getNetworkType();

    void setNetworkType(BlockchainNetworkType networkType);
}
