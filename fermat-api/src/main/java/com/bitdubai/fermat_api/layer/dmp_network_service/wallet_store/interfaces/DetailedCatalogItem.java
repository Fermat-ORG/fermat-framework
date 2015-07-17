package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 7/17/15.
 */
public interface DetailedCatalogItem {

    public int getDefaultSizeInBytes();

    public String getDeveloperName();

    public String getDeveloperPublicKey();

    /**
     * Given a wallet id this method find the languages supported in the said wallet.
     *
     * @return the list of languages supported
     */
    public List<String> getLanguages();

    public SkinPreview getSkin(UUID skinId);

    public List<UUID> getSkinsList();

    public byte[] getWalletIcon() throws CantGetWalletIconException;

    public UUID getWalletIdInCatalog();

    public String getWalletDescription();

    public String getWalletName();

    public String getWalletVersion();
}
