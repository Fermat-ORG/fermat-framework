package com.bitdubai.sub_app.wallet_store.common.model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreDetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Developer;
import com.wallet_store.bitdubai.R;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Representa la informacion de un item de la lista de wallets que esta en el catalogo de la Wallet Store.
 *
 * @author Nelson Ramirez
 */
public class CatalogItemDao implements Serializable {
    private static final long serialVersionUID = -8730067026050196759L;

    private String developerName;
    private String installationStatusText;
    private String walletName;
    private Drawable walletIcon;


    /**
     * Crea un nuevo CatalogItemDao
     *
     * @param catalogueItem un item del catalogo
     * @throws DatailedInformationNotFoundException
     * @throws CantGetWalletIconException
     */
    public CatalogItemDao(WalletStoreCatalogueItem catalogueItem) throws DatailedInformationNotFoundException, CantGetWalletIconException {

        walletName = catalogueItem.getName();

        InstallationStatus installationStatus = catalogueItem.getInstallationStatus();
        installationStatusText = installationStatus.toString();

        WalletStoreDetailedCatalogItem detailedCatalogItem = catalogueItem.getWalletDetailedCatalogItem();
        Developer developer = detailedCatalogItem.getDeveloper();
        developerName = developer.getName();

        byte[] iconBytes = catalogueItem.getIcon();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(iconBytes);
        walletIcon = Drawable.createFromStream(inputStream, "walletIcon");
    }

    public String getDeveloperName() {
        return developerName;
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


    private CatalogItemDao(String walletName, String developerName, String installationStatusText, Drawable walletIcon) {
        this.walletName = walletName;
        this.developerName = developerName;
        this.installationStatusText = installationStatusText;
        this.walletIcon = walletIcon;
    }

    public static ArrayList<CatalogItemDao> getTestData(Resources res) {

        String[] walletNames = {"Girl's wallet", "Boy's wallet", "Ladies", "Young", "Boca Junior's wallet",
                "Carrefour's wallet", "Gucci's wallet", "Bank Itau's wallet", "Mc donal's wallet", "Van's wallet",
                "Samsung's wallet", "Popular Bank's wallet", "Sony's wallet", "BMW's wallet", "HP's wallet",
                "Billabong's wallet", "Starbuck's wallet"};

        String[] developerNames = {"by bitDubai", "by bitDubai", "by bitDubai", "by bitDubai", "by Boca Junios",
                "by carrefour", "by Gucci", "by Bank Itau", "by McDonals", "by Vans", "by Samsung", "by bitDubai",
                "by Sony", "by BMW", "by HP", "by Billabong", "by starbucks"};

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

        ArrayList<CatalogItemDao> testItems = new ArrayList<>();
        for (int i = 0; i < walletIcons.length && i < installed.length && i < prices.length && i < developerNames.length && i < walletNames.length; i++) {
            String installedStr = installed[i] ? "INSTALLED" : prices[i];
            Drawable icon = res.getDrawable(walletIcons[i]);
            CatalogItemDao item = new CatalogItemDao(walletNames[i], developerNames[i], installedStr, icon);
            testItems.add(item);
        }

        return testItems;
    }
}
