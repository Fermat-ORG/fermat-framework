package com.bitdubai.sub_app.wallet_store.common.models;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreDetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Developer;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.wallet_store.bitdubai.R;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created on 22/08/15.
 * Representa la informacion de un item de la lista de wallets que esta en el catalogo de la Wallet Store.
 *
 * @author Nelson Ramirez
 */
public class CatalogueItemDao implements Serializable {
    private static final long serialVersionUID = -8730067026050196759L;


    private String installationStatusText;
    private String walletName;
    private String description;
    private Drawable walletIcon;
    private UUID id;


    /**
     * Crea un nuevo CatalogueItemDao
     *
     * @param catalogueItem un item del catalogo
     * @param res           resource object to generate the icon
     */
    public CatalogueItemDao(WalletStoreCatalogueItem catalogueItem, Resources res) {

        id = catalogueItem.getId();

        walletName = catalogueItem.getName();

        description = catalogueItem.getDescription();

        InstallationStatus installationStatus = catalogueItem.getInstallationStatus();
        installationStatusText = installationStatus.toString();


        try {
            byte[] iconBytes = catalogueItem.getIcon();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(iconBytes);
            walletIcon = Drawable.createFromStream(inputStream, "walletIcon");
        } catch (Exception e) {
            walletIcon = res.getDrawable(R.drawable.wallet_1);
        }

    }


    public String getDescription() {
        return description;
    }

    public String getInstallationStatusText() {
        return installationStatusText;
    }

    public String getWalletName() {
        return walletName;
    }

    public Drawable getWalletIcon() {
        return walletIcon;
    }

    public UUID getId() {
        return id;
    }


    private CatalogueItemDao(String walletName, String installationStatusText, Drawable walletIcon) {
        this.walletName = walletName;
        this.installationStatusText = installationStatusText;
        this.walletIcon = walletIcon;
    }

    public static ArrayList<CatalogueItemDao> getTestData(Resources res) {

        String[] walletNames = {"Girl's wallet", "Boy's wallet", "Ladies", "Young", "Boca Junior's wallet",
                "Carrefour's wallet", "Gucci's wallet", "Bank Itau's wallet", "Mc donal's wallet", "Van's wallet",
                "Samsung's wallet", "Popular Bank's wallet", "Sony's wallet", "BMW's wallet", "HP's wallet",
                "Billabong's wallet", "Starbuck's wallet"};

        String[] prices = {"FREE", "$9.25", "FREE", "$7.65", "$5.32", "FREE", "$3.98", "FREE", "$0.24", "$3.40",
                "FREE", "FREE", "20.01", "$1.00", "FREE", "FREE", "$3.21"};

        boolean[] installed = {true, true, true, true, true, true, true, true, false, false, false, true,
                false, false, false, false, false};

        int[] walletIcons = {R.drawable.wallet_store_cover_photo_girl, R.drawable.wallet_store_cover_photo_boy,
                R.drawable.wallet_store_cover_photo_lady, R.drawable.wallet_store_cover_photo_young,
                R.drawable.wallet_store_cover_photo_boca_juniors, R.drawable.wallet_store_cover_photo_carrefour,
                R.drawable.wallet_store_cover_photo_gucci, R.drawable.wallet_store_cover_photo_bank_itau,
                R.drawable.wallet_store_cover_photo_mcdonals, R.drawable.wallet_store_cover_photo_vans,
                R.drawable.wallet_store_cover_photo_samsung, R.drawable.wallet_store_cover_photo_bank_popular,
                R.drawable.wallet_store_cover_photo_sony, R.drawable.wallet_store_cover_photo_bmw,
                R.drawable.wallet_store_cover_photo_hp, R.drawable.wallet_store_cover_photo_billabong,
                R.drawable.wallet_store_cover_photo_starbucks};

        ArrayList<CatalogueItemDao> testItems = new ArrayList<>();
        for (int i = 0; i < walletIcons.length && i < installed.length && i < prices.length && i < walletNames.length; i++) {
            String installedStr = installed[i] ? "INSTALLED" : prices[i];
            Drawable icon = res.getDrawable(walletIcons[i]);
            CatalogueItemDao item = new CatalogueItemDao(walletNames[i], installedStr, icon);
            testItems.add(item);
        }

        return testItems;
    }


}
