package com.bitdubai.fermat_android_api.engine;

/**
 * Created by Matias Furszyfer on 2016.05.21..
 * <p/>
 * This interface will for internal FermatApps and external android apps to open the differents elements in Fermat. for example the desktop screen, a specific wallet, etc..
 */
public interface FermatApplicationCaller {


    /**
     * Open the Fermat home screen
     */
    void openFermatHome();

    /**
     * Open an specific App running on Fermat
     *
     * @param appPublicKey
     * @throws Exception
     */
    void openFermatApp(String appPublicKey) throws Exception;

    /**
     * Open the recents screen, this screen shows every Fermat app opened.
     */
    void openRecentsScreen();

}
