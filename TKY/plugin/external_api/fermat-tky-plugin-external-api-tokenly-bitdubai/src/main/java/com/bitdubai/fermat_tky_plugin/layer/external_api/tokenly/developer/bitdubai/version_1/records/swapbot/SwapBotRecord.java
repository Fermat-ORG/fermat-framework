package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.swapbot;


import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.ImageDetails;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Swap;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.TokenlyBalance;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/03/16.
 */
public class SwapBotRecord implements Bot, Serializable {

    private String id;
    private String name;
    private String address;
    private String username;
    private String description;
    private String descriptionHtml;
    private ImageDetails backgroundImageDetails;
    private ImageDetails logoImageDetails;
    private String[] backgroundOverlaySettings;
    private Swap[] swaps;
    private TokenlyBalance[] tokenlyBalances;
    private TokenlyBalance[][] allTokenlyBalancesByType;
    private float returnFee;
    private String state;
    private long confirmationsRequired;
    private long refundsAfterBlocks;
    private Date createdAt;
    private String hash;
    private String botUrl;

    public SwapBotRecord(
            String id,
            String name,
            String address,
            String username,
            String description,
            String descriptionHtml,
            ImageDetails backgroundImageDetails,
            ImageDetails logoImageDetails,
            String[] backgroundOverlaySettings,
            Swap[] swaps,
            TokenlyBalance[] tokenlyBalances,
            TokenlyBalance[][] allTokenlyBalancesByType,
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
        this.backgroundOverlaySettings = backgroundOverlaySettings;
        this.swaps = swaps;
        this.tokenlyBalances = tokenlyBalances;
        this.allTokenlyBalancesByType = allTokenlyBalancesByType;
        this.returnFee = returnFee;
        this.state = state;
        this.confirmationsRequired = confirmationsRequired;
        this.refundsAfterBlocks = refundsAfterBlocks;
        this.createdAt = createdAt;
        this.hash = hash;
        //Set the default botURL;
        this.botUrl = "http://tokenly.com";
    }

    /**
     * Default constructor with parameters
     * @param id
     * @param name
     * @param address
     * @param username
     * @param description
     * @param descriptionHtml
     * @param backgroundImageDetails
     * @param logoImageDetails
     * @param backgroundOverlaySettings
     * @param swaps
     * @param tokenlyBalances
     * @param allTokenlyBalancesByType
     * @param returnFee
     * @param state
     * @param confirmationsRequired
     * @param refundsAfterBlocks
     * @param createdAt
     * @param hash
     * @param botUrl
     */
    public SwapBotRecord(
            String id,
            String name,
            String address,
            String username,
            String description,
            String descriptionHtml,
            ImageDetails backgroundImageDetails,
            ImageDetails logoImageDetails,
            String[] backgroundOverlaySettings,
            Swap[] swaps,
            TokenlyBalance[] tokenlyBalances,
            TokenlyBalance[][] allTokenlyBalancesByType,
            float returnFee,
            String state,
            long confirmationsRequired,
            long refundsAfterBlocks,
            Date createdAt,
            String hash,
            String botUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.username = username;
        this.description = description;
        this.descriptionHtml = descriptionHtml;
        this.backgroundImageDetails = backgroundImageDetails;
        this.logoImageDetails = logoImageDetails;
        this.backgroundOverlaySettings = backgroundOverlaySettings;
        this.swaps = swaps;
        this.tokenlyBalances = tokenlyBalances;
        this.allTokenlyBalancesByType = allTokenlyBalancesByType;
        this.returnFee = returnFee;
        this.state = state;
        this.confirmationsRequired = confirmationsRequired;
        this.refundsAfterBlocks = refundsAfterBlocks;
        this.createdAt = createdAt;
        this.hash = hash;
        this.botUrl = botUrl;
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
        return this.backgroundOverlaySettings;
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
    public TokenlyBalance[][] getAllBalancesByType() {
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

    /**
     * This method returns the botUrl
     * @return
     */
    @Override
    public String getBotUrl(){
        return this.botUrl;
    }

    /**
     * This method sets the botUrl
     * @param botUrl
     */
    public void setBotUrl(String botUrl){
        this.botUrl = botUrl;
    }

    @Override
    public String toString() {
        return "SwapBotRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", descriptionHtml='" + descriptionHtml + '\'' +
                ", backgroundImageDetails=" + backgroundImageDetails +
                ", logoImageDetails=" + logoImageDetails +
                ", backgroundOverlaySettings=" + Arrays.toString(backgroundOverlaySettings) +
                ", swaps=" + Arrays.toString(swaps) +
                ", tokenlyBalances=" + Arrays.toString(tokenlyBalances) +
                ", allTokenlyBalancesByType=" + Arrays.toString(allTokenlyBalancesByType) +
                ", returnFee=" + returnFee +
                ", state='" + state + '\'' +
                ", confirmationsRequired=" + confirmationsRequired +
                ", refundsAfterBlocks=" + refundsAfterBlocks +
                ", createdAt=" + createdAt +
                ", hash='" + hash + '\'' +
                ", botUrl='" + botUrl + '\'' +
                '}';
    }
}
