package com.bitdubai.fermat_art_api.layer.wallet.fan_song_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.layer.wallet.fan_song_wallet.exceptions.CantCreateFanSongWalletException;
import com.bitdubai.fermat_art_api.layer.wallet.fan_song_wallet.exceptions.CantLoadFanSongWalletException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public interface FanSongWalletManager extends FermatManager {

    /**
     * This method creates a wallet by given public key and external platform.
     * @param walletPublicKey
     * @param externalPlatform
     * @throws CantCreateFanSongWalletException
     */
    void createFanSongWallet(
            String walletPublicKey,
            ExternalPlatform externalPlatform) throws CantCreateFanSongWalletException;

    /**
     * This method load a Fan song wallet by public key and external platform.
     * @param walletPublicKey
     * @param externalPlatform
     * @return
     * @throws CantLoadFanSongWalletException
     */
    FanSongWallet loadFanSongWallet(
            String walletPublicKey,
            ExternalPlatform externalPlatform) throws CantLoadFanSongWalletException;

}
