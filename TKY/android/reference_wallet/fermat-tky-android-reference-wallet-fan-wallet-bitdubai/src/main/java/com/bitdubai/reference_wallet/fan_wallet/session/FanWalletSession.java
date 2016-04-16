package com.bitdubai.reference_wallet.fan_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModule;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Created by Miguel Payarez on 14/03/16.
 */
public class FanWalletSession extends AbstractFermatSession<InstalledWallet,FanWalletModule,WalletResourcesProviderManager> {

    public static final String CATALOG="artist_catalog";
    public static final String MY_SONG="song_list";

    //TODO wait to define method in module
/*    public List<catalog> getArtistCatalog() {
        Object data = getData(CATALOG);
        return (data != null) ? (List<catalog>) data : null;
    }

    public List<songs> getSongList() {
        Object data = getData(MY_SONG);
        return (data != null) ? (List<song>) data : null;
    }*/


}
