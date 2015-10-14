package com.bitdubai.sub_app.wallet_store.common.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.wallet_store.bitdubai.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created on 22/08/15.
 * Representa la informacion de un item de la lista de wallets que esta en el catalogo de la Wallet Store.
 *
 * @author Nelson Ramirez
 */
public class WalletStoreListItem implements Serializable {
    private static final long serialVersionUID = -8730067026050196759L;


    private InstallationStatus installationStatus;
    private String walletName;
    private String description;
    private Bitmap walletIcon;
    private UUID id;
    private WalletCategory category;
    private boolean testData;


    /**
     * Crea un nuevo WalletStoreListItem
     *
     * @param catalogueItem un item del catalogo
     * @param res           resource object to generate the icon
     */
    public WalletStoreListItem(WalletStoreCatalogueItem catalogueItem, Resources res) {
        testData = false;

        id = catalogueItem.getId();

        category = catalogueItem.getCategory();

        walletName = catalogueItem.getName();

        description = catalogueItem.getDescription();

        installationStatus = catalogueItem.getInstallationStatus();


        try {
            byte[] iconBytes = catalogueItem.getIcon();
            walletIcon = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length);
        } catch (Exception e) {
            walletIcon = BitmapFactory.decodeResource(res, R.drawable.wallet_1);
        }

    }


    public String getDescription() {
        return description;
    }

    public WalletCategory getCategory() {
        return category;
    }

    public UUID getId() {
        return id;
    }

    public InstallationStatus getInstallationStatus() {
        return installationStatus;
    }

    public String getWalletName() {
        return walletName;
    }

    public Bitmap getWalletIcon() {
        return walletIcon;
    }


    private WalletStoreListItem(String walletName, InstallationStatus installationStatus, Bitmap walletIcon) {
        this.walletName = walletName;
        this.installationStatus = installationStatus;
        this.walletIcon = walletIcon;
        this.testData = true;
    }

    public static ArrayList<WalletStoreListItem> getTestData(Resources res) {

        String[] walletNames = {"Girl's wallet", "Boy's wallet", "Ladies", "Young", "Boca Junior's wallet",
                "Carrefour's wallet", "Gucci's wallet", "Bank Itau's wallet", "Mc donal's wallet", "Van's wallet",
                "Samsung's wallet", "Popular Bank's wallet", "Sony's wallet", "BMW's wallet", "HP's wallet",
                "Billabong's wallet", "Starbuck's wallet"};

        String[] prices = {"FREE", "$9.25", "FREE", "$7.65", "$5.32", "FREE", "$3.98", "FREE", "$0.24", "$3.40",
                "FREE", "FREE", "20.01", "$1.00", "FREE", "FREE", "$3.21"};

        InstallationStatus[] installed = {InstallationStatus.INSTALLED, InstallationStatus.INSTALLED, InstallationStatus.INSTALLED,
                InstallationStatus.INSTALLED, InstallationStatus.INSTALLED, InstallationStatus.INSTALLED, InstallationStatus.INSTALLED,
                InstallationStatus.INSTALLED, InstallationStatus.NOT_INSTALLED, InstallationStatus.UPGRADE_AVAILABLE, InstallationStatus.NOT_INSTALLED,
                InstallationStatus.INSTALLED, InstallationStatus.UPGRADE_AVAILABLE, InstallationStatus.NOT_INSTALLED, InstallationStatus.NOT_INSTALLED,
                InstallationStatus.NOT_INSTALLED, InstallationStatus.NOT_INSTALLED};

        int[] walletIcons = {R.drawable.wallet_store_cover_photo_girl, R.drawable.wallet_store_cover_photo_boy,
                R.drawable.wallet_store_cover_photo_lady, R.drawable.wallet_store_cover_photo_young,
                R.drawable.wallet_store_cover_photo_boca_juniors, R.drawable.wallet_store_cover_photo_carrefour,
                R.drawable.wallet_store_cover_photo_gucci, R.drawable.wallet_store_cover_photo_bank_itau,
                R.drawable.wallet_store_cover_photo_mcdonals, R.drawable.wallet_store_cover_photo_vans,
                R.drawable.wallet_store_cover_photo_samsung, R.drawable.wallet_store_cover_photo_bank_popular,
                R.drawable.wallet_store_cover_photo_sony, R.drawable.wallet_store_cover_photo_bmw,
                R.drawable.wallet_store_cover_photo_hp, R.drawable.wallet_store_cover_photo_billabong,
                R.drawable.wallet_store_cover_photo_starbucks};

        ArrayList<WalletStoreListItem> testItems = new ArrayList<>();
        for (int i = 0; i < walletIcons.length && i < installed.length && i < prices.length && i < walletNames.length; i++) {
            Bitmap icon = BitmapFactory.decodeResource(res, walletIcons[i]);
            WalletStoreListItem item = new WalletStoreListItem(walletNames[i], installed[i], icon);
            testItems.add(item);
        }

        return testItems;
    }


    public boolean isTestData() {
        return testData;
    }
}
