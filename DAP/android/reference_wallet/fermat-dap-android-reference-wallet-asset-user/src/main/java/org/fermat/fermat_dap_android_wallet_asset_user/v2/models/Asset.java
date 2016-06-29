package org.fermat.fermat_dap_android_wallet_asset_user.v2.models;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;

import org.fermat.fermat_dap_android_wallet_asset_user.util.Utils;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class Asset implements Serializable {

    private AssetUserWalletList assetUserWalletList;
    private AssetUserWalletTransaction assetUserWalletTransaction;
    private DigitalAsset digitalAsset;

    private String id;
    private byte[] image;
    private String name;
    private double amount;
    private String description;
    private Date expDate;
    private Date date;
    private Status status;
    private String actorName;
    private byte[] actorImage;
    private boolean redeemable;
    private boolean transferable;
    private boolean saleable;

    private AssetUserNegotiation assetUserNegotiation;

    public void setDigitalAsset(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    public enum Status {
        PENDING("PENDING"),
        CONFIRMED("CONFIRMED");

        private String desc;

        Status(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public Asset() {

    }

    public Asset(String name) {
        this.name = name;
    }

    public Asset(AssetUserWalletList assetUserWalletList, List<AssetUserWalletTransaction> transactions, CryptoAddress cryptoAddress) {
        if (transactions.isEmpty())
            throw new IllegalStateException("Can't initialize this object without transactions!!");
        AssetUserWalletTransaction lastTransaction = transactions.get(transactions.size() - 1);
        AssetUserWalletTransaction firstTransaction = transactions.get(0);
        this.assetUserWalletTransaction = lastTransaction;
        this.assetUserWalletList = assetUserWalletList;
        this.digitalAsset = DigitalAsset.copyAsset(assetUserWalletList.getDigitalAsset());
        this.digitalAsset.setGenesisAddress(cryptoAddress);
        setId(assetUserWalletTransaction.getTransactionHash());
        if (digitalAsset.getResources().size() != 0) {
            setImage(digitalAsset.getResources().get(0).getResourceBinayData());
        }
        setName(digitalAsset.getName());
        setAmount(assetUserWalletList.getAvailableBalance());
        setDescription(digitalAsset.getDescription());
        setExpDate((Date) digitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
        setDate(new Date(lastTransaction.getTimestamp()));
        setStatus((lastTransaction.getBalanceType().equals(BalanceType.AVAILABLE) && lastTransaction.getTransactionType().equals(TransactionType.CREDIT)) ? Status.CONFIRMED : Status.PENDING);
        setActorName(firstTransaction.getActorFrom().getName());
        setActorImage(firstTransaction.getActorFrom().getProfileImage());
        setRedeemable((Boolean) digitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getValue());
        setTransferable((Boolean) digitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.TRANSFERABLE).getValue());
        setSaleable((Boolean) digitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.SALEABLE).getValue());
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFormattedAmount() {
        return DAPStandardFormats.BITCOIN_FORMAT.format(BitcoinConverter.convert(amount, SATOSHI, BITCOIN)) + " " + BITCOIN.getName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormattedExpDate() {
        return (expDate == null) ? "No expiration date" : DAPStandardFormats.DATE_FORMAT.format(expDate);
    }

    public String getFormattedDate() {
        return (date == null) ? "No date" : Utils.getTimeAgo(date.getTime());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    public byte[] getActorImage() {
        return actorImage;
    }

    public void setActorImage(byte[] actorImage) {
        this.actorImage = actorImage;
    }

    public boolean isRedeemable() {
        return redeemable;
    }

    public void setRedeemable(boolean redeemable) {
        this.redeemable = redeemable;
    }

    public boolean isTransferable() {
        return transferable;
    }

    public void setTransferable(boolean transferable) {
        this.transferable = transferable;
    }

    public boolean isSaleable() {
        return saleable;
    }

    public void setSaleable(boolean saleable) {
        this.saleable = saleable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AssetUserNegotiation getAssetUserNegotiation() {
        return assetUserNegotiation;
    }

    public void setAssetUserNegotiation(AssetUserNegotiation assetUserNegotiation) {
        this.assetUserNegotiation = assetUserNegotiation;
    }

    public AssetUserWalletTransaction getAssetUserWalletTransaction() {
        return assetUserWalletTransaction;
    }
}
