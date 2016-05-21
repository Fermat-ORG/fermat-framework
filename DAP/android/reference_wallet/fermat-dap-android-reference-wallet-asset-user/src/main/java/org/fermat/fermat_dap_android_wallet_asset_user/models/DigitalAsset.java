package org.fermat.fermat_dap_android_wallet_asset_user.models;

import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;

import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by francisco on 08/10/15.
 */
public class DigitalAsset {

    private String name;
    private String amount;
    private org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset;
    private Long availableBalanceQuantity;
    private Long bookBalanceQuantity;
    private Long availableBalance;
    private Timestamp expDate;
    private String walletPublicKey;
    private String assetPublicKey;
    private ActorAssetUser actorAssetUser;
    private byte[] image;
    int lockedAssets;


    private UserAssetNegotiation userAssetNegotiation;

    private int redeemed;
    private int appropriated;
    private int unused;

    public DigitalAsset() {
    }

    public DigitalAsset(String name, String amount) {
        setName(name);
        setAmount(amount);
    }

    public static ArrayList<DigitalAsset> getAssets() {
        List<DigitalAsset> assets = new ArrayList<>();
        assets.add(new DigitalAsset("KFC Coupon", "150.457"));
        assets.add(new DigitalAsset("Burgerking Coupon", "150.457"));
        assets.add(new DigitalAsset("MacDonalds Coupon", "150.457"));
        assets.add(new DigitalAsset("Free Coupon", "150.457"));
        return (ArrayList<DigitalAsset>) assets;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%s | %s BTC", name, amount);
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    public ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public Long getAvailableBalanceQuantity() {
        return availableBalanceQuantity;
    }

    public void setAvailableBalanceQuantity(Long availableBalanceQuantity) {
        this.availableBalanceQuantity = availableBalanceQuantity;
    }

    public Long getUsableAssetsQuantity() {
        return getAvailableBalanceQuantity() - getLockedAssets();
    }

    public Long getBookBalanceQuantity() {
        return bookBalanceQuantity;
    }

    public void setBookBalanceQuantity(Long bookBalanceQuantity) {
        this.bookBalanceQuantity = bookBalanceQuantity;
    }

    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Long getAvailableBalance() {
        return availableBalance;
    }

    public Double getAvailableBalanceBitcoin() {
        return BitcoinConverter.convert(Double.valueOf(availableBalance), SATOSHI, BITCOIN);
    }

    public String getFormattedAvailableBalanceBitcoin() {
        return DAPStandardFormats.BITCOIN_FORMAT.format(getAvailableBalanceBitcoin());
    }

    public Date getExpDate() {
        return expDate;
    }

    public String getFormattedExpDate() {
        if (expDate == null) return "No expiration date";
        return DAPStandardFormats.DATE_FORMAT.format(expDate);
    }

    public void setExpDate(Timestamp expDate) {
        this.expDate = expDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(int redeemed) {
        this.redeemed = redeemed;
    }

    public int getAppropriated() {
        return appropriated;
    }

    public void setAppropriated(int appropriated) {
        this.appropriated = appropriated;
    }

    public int getUnused() {
        return unused;
    }

    public int getLockedAssets() {
        return lockedAssets;
    }

    public void setLockedAssets(int lockedAssets) {
        this.lockedAssets = lockedAssets;
    }

    public void setUnused(int unused) {
        this.unused = unused;
    }

    public void setUserAssetNegotiation(UserAssetNegotiation userAssetNegotiation) {
        this.userAssetNegotiation = userAssetNegotiation;
    }

    public UserAssetNegotiation getUserAssetNegotiation() {
        return userAssetNegotiation;
    }

    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    public void setDigitalAsset(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }
}
