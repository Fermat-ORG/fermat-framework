package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 5/01/16.
 */
public class AssetFactoryRecord implements AssetFactory {

    //VARIABLE DECLARATION
    private String factoryId;
    private String walletPublicKey;
    private String publicKey;

    {
        publicKey = new ECCKeyPair().getPublicKey();
    }

    private String name;
    private String description;
    private List<Resource> resources;
    private DigitalAssetContract digitalAssetContract;
    private State state;
    private List<ContractProperty> contractProperties;
    private int quantity;
    private int totalQuantity;
    private long amount;
    private long fee;
    private Timestamp creationTimestamp;
    private Timestamp lastModificationTimestamp;
    private boolean isRedeemable;
    private boolean isTransferable;
    private boolean isExchangeable;
    private Timestamp expirationDate;
    private AssetBehavior assetBehavior;
    private IdentityAssetIssuer identityAssetIssuer;
    private BlockchainNetworkType networkType;

    //CONSTRUCTORS

    public AssetFactoryRecord() {
    }

    public AssetFactoryRecord(String factoryId, String walletPublicKey, String publicKey, String name, String description, List<Resource> resources, DigitalAssetContract digitalAssetContract, State state, List<ContractProperty> contractProperties, int quantity, int totalQuantity, long amount, long fee, Timestamp creationTimestamp, Timestamp lastModificationTimestamp, boolean isRedeemable, boolean isTransferable, boolean isExchangeable, Timestamp expirationDate, AssetBehavior assetBehavior, IdentityAssetIssuer identityAssetIssuer, BlockchainNetworkType networkType) {
        this.factoryId = factoryId;
        this.walletPublicKey = walletPublicKey;
        this.publicKey = publicKey;
        this.name = name;
        this.description = description;
        this.resources = resources;
        this.digitalAssetContract = digitalAssetContract;
        this.state = state;
        this.contractProperties = contractProperties;
        this.quantity = quantity;
        this.totalQuantity = totalQuantity;
        this.amount = amount;
        this.fee = fee;
        this.creationTimestamp = creationTimestamp;
        this.lastModificationTimestamp = lastModificationTimestamp;
        this.isRedeemable = isRedeemable;
        this.isTransferable = isTransferable;
        this.isExchangeable = isExchangeable;
        this.expirationDate = expirationDate;
        this.assetBehavior = assetBehavior;
        this.identityAssetIssuer = identityAssetIssuer;
        this.networkType = networkType;
    }

    //PUBLIC METHODS

    @Override
    public IdentityAssetIssuer getIdentyAssetIssuer() {
        return identityAssetIssuer;
    }

    @Override
    public void setIdentityAssetIssuer(IdentityAssetIssuer identityAssetIssuer) {
        this.identityAssetIssuer = identityAssetIssuer;
    }

    @Override
    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    @Override
    public void setNetworkType(BlockchainNetworkType networkType) {
        this.networkType = networkType;
    }

    @Override
    public String getFactoryId() {
        return factoryId;
    }

    @Override
    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    @Override
    public String getAssetPublicKey() {
        return publicKey;
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<Resource> getResources() {
        return resources;
    }

    @Override
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public DigitalAssetContract getContract() {
        return digitalAssetContract;
    }

    @Override
    public void setContract(DigitalAssetContract contract) {
        this.digitalAssetContract = contract;
    }

    @Override
    public List<ContractProperty> getContractProperties() {
        return contractProperties;
    }

    @Override
    public void setContractProperties(List<ContractProperty> contractProperties) {
        this.contractProperties = contractProperties;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getTotalQuantity() {
        return totalQuantity;
    }

    @Override
    public void setTotalQuantity(int quantity) {
        this.totalQuantity = quantity;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public long getFee() {
        return fee;
    }

    @Override
    public void setFee(long fee) {
        this.fee = fee;
    }

    @Override
    public boolean getIsRedeemable() {
        return isRedeemable;
    }

    @Override
    public void setIsRedeemable(boolean isRedeemable) {
        this.isRedeemable = isRedeemable;
    }

    @Override
    public boolean getIsTransferable() {
        return isTransferable;
    }

    @Override
    public void setIsTransferable(boolean isTransferable) {
        this.isTransferable = isTransferable;
    }

    @Override
    public boolean getIsExchangeable() {
        return isExchangeable;
    }

    @Override
    public void setIsExchangeable(boolean isExchangeable) {
        this.isExchangeable = isExchangeable;
    }

    @Override
    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public AssetBehavior getAssetBehavior() {
        return assetBehavior;
    }

    @Override
    public void setAssetBehavior(AssetBehavior assetBehavior) {
        this.assetBehavior = assetBehavior;
    }

    @Override
    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public void setCreationTimestamp(Timestamp timestamp) {
        this.creationTimestamp = timestamp;
    }

    @Override
    public Timestamp getLastModificationTimestamp() {
        return lastModificationTimestamp;
    }

    @Override
    public void setLastModificationTimeststamp(Timestamp timestamp) {
        this.lastModificationTimestamp = timestamp;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
