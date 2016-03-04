package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models;

import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

import java.sql.Timestamp;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class Asset {
    private AssetUserWalletList assetUserWalletList;
    private DigitalAsset digitalAsset;

    private byte[] image;
    private String name;
    private double amount;
    private String description;
    private Timestamp expDate;
    private Status status;
    private String actorName;

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

    public Asset(String name) {
        this.name = name;
    }

    public Asset(AssetUserWalletList assetUserWalletList, Status status) {
        this.assetUserWalletList = assetUserWalletList;
        this.digitalAsset = assetUserWalletList.getDigitalAsset();
        setImage(digitalAsset.getResources().get(0).getResourceBinayData());
        setName(digitalAsset.getName());
        setAmount(assetUserWalletList.getAvailableBalance());
        setDescription(digitalAsset.getDescription());
        setExpDate((Timestamp) digitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
        setStatus(status);
        setActorName(digitalAsset.getIdentityAssetIssuer().getAlias());
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

    public Timestamp getExpDate() {
        return expDate;
    }

    public void setExpDate(Timestamp expDate) {
        this.expDate = expDate;
    }

    public String getFormattedExpDate() {
        return (expDate == null) ? "No expiration date" : DAPStandardFormats.DATE_FORMAT.format(expDate);
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
}
