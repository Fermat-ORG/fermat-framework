package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetFactoryProjectResourceException;

/**
 * Created by ciencias on 7/15/15.
 */
public interface WalletFactoryProjectSkin {



    public byte[] getImageResource(String imageName) throws CantGetFactoryProjectResourceException;

    public byte[] getVideoResource(String videoName) throws CantGetFactoryProjectResourceException;

    public byte[] getSoundResource(String soundName) throws CantGetFactoryProjectResourceException;

    public String getLayoutResource(String layoutName) throws CantGetFactoryProjectResourceException;

    // TODO: add , update , delete
    public void addImageResource(String imageName, byte[] image) throws CantAddWalletFactoryProjectResource;
    public void addVideoResource(String videoName, byte[] image) throws CantAddWalletFactoryProjectResource;
    public void addSoundResource(String soundName, byte[] image) throws CantAddWalletFactoryProjectResource;
    public void addFontStyleResource(String fontStyleName, String ttfFileContent) throws CantAddWalletFactoryProjectResource;

}
