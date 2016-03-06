package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.records;

import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Bot;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.ImageDetails;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Swap;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.TokenlyBalance;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/03/16.
 */
public class SwapBotRecord implements Bot {

    private String id;
    private String name;
    private String address;
    private String username;
    private String description;
    private String descriptionHtml;
    private ImageDetails backgroundImageDetails;
    private ImageDetails logoImageDetails;
    private String[] backgroudOverlaySettings;
    private Swap[] swaps;
    private TokenlyBalance[] tokenlyBalances;
    private TokenlyBalance[] allTokenlyBalancesByType;
    private float returnFee;
    private String state;
    private long confirmationsRequired;
    private long refundsAfterBlocks;
    private Date createdAt;
    private String hash;

    public SwapBotRecord(
            String id,
            String name,
            String address,
            String username,
            String description,
            String descriptionHtml,
            ImageDetails backgroundImageDetails,
            ImageDetails logoImageDetails,
            String[] backgroudOverlaySettings,
            Swap[] swaps,
            TokenlyBalance[] tokenlyBalances,
            TokenlyBalance[] allTokenlyBalancesByType,
            float returnFee,
            String state,
            long confirmationsRequired,
            long refundsAfterBlocks,
            Date createdAt,
            String hash) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.username = username;
        this.description = description;
        this.descriptionHtml = descriptionHtml;
        this.backgroundImageDetails = backgroundImageDetails;
        this.logoImageDetails = logoImageDetails;
        this.backgroudOverlaySettings = backgroudOverlaySettings;
        this.swaps = swaps;
        this.tokenlyBalances = tokenlyBalances;
        this.allTokenlyBalancesByType = allTokenlyBalancesByType;
        this.returnFee = returnFee;
        this.state = state;
        this.confirmationsRequired = confirmationsRequired;
        this.refundsAfterBlocks = refundsAfterBlocks;
        this.createdAt = createdAt;
        this.hash = hash;
    }

    /**
     * This method returns the bot id.
     * @return
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * This method return the bot name
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * This method returns the address
     * @return
     */
    @Override
    public String getAddress() {
        return this.address;
    }

    /**
     * This method returns the user name
     * @return
     */
    @Override
    public String getUserName() {
        return this.username;
    }

    /**
     * This method returns the bot description
     * @return
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * This method returns the html bot description.
     * @return
     */
    @Override
    public String getDescriptionHtml() {
        return this.descriptionHtml;
    }

    /**
     * This method the background image details
     * @return
     */
    @Override
    public ImageDetails getBackgroundImageDetails() {
        return this.backgroundImageDetails;
    }

    /**
     * This method returns the logo image details
     * @return
     */
    @Override
    public ImageDetails getLogoImageDetails() {
        return this.logoImageDetails;
    }

    /**
     * This method returns the background overlay settings
     * @return
     */
    @Override
    public String[] getBackgroundOverlaySettings() {
        return this.backgroudOverlaySettings;
    }

    /**
     * This method returns the swap bots
     * @return
     */
    @Override
    public Swap[] getSwaps() {
        return this.swaps;
    }

    /**
     * This method returns the swap bot balances
     * @return
     */
    @Override
    public TokenlyBalance[] getBalances() {
        return this.tokenlyBalances;
    }

    /**
     * This method returns the swap bot balances by type
     * @return
     */
    @Override
    public TokenlyBalance[] getAllBalancesByType() {
        return this.allTokenlyBalancesByType;
    }

    /**
     * This method return the return fee.
     * @return
     */
    @Override
    public float getReturnFee() {
        return this.returnFee;
    }

    /**
     * This method returns the swap bot state.
     * @return
     */
    @Override
    public String getState() {
        return this.state;
    }

    /**
     * This method returns the confirmations required
     * @return
     */
    @Override
    public long getConfirmationsRequired() {
        return this.confirmationsRequired;
    }

    /**
     * This method returns the refund after blocks
     * @return
     */
    @Override
    public long getRefundAfterBlocks() {
        return this.refundsAfterBlocks;
    }

    /**
     * This method returns the creation date
     * @return
     */
    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * This method return the bot hash
     * @return
     */
    @Override
    public String getHash() {
        return this.hash;
    }
}
