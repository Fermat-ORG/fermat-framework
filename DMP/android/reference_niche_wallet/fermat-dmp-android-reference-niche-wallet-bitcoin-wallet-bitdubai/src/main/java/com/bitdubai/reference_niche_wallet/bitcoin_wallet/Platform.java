package com.bitdubai.reference_niche_wallet.bitcoin_wallet;

/**
 * Created by Natalia on 4/4/15.
 */

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.DealsWithNicheWalletTypeCryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class Platform implements DealsWithErrors, DealsWithEvents, DealsWithNicheWalletTypeCryptoWallet,  Serializable {

    /**
     * DealsWithWalletContacts Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private static ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;


    /**
     *  Reference wallet style
     */


    /**
     * Font Style
     */
    public static final int LARGE_FONT=16;
    public static final int MEDIUM_FONT=12;
    public static final int SMALL_FONT=8;


    /**
     *  Amount types
     */

    public static final int TYPE_BTC=1;
    public static final int TYPE_BITS=2;

    /**
     *  Options selected
     */

    public static BalanceType TYPE_BALANCE_TYPE_SELECTED=BalanceType.AVAILABLE;

    public static int TYPE_AMOUNT_SELECTED=TYPE_BTC;

    /**
     * DealsWithWalletContacts Interface implementation.
     */
    public void setNicheWalletTypeCryptoWalletManager(CryptoWalletManager cryptoWalletManager) {
        this.cryptoWalletManager = cryptoWalletManager;
    }


    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * Platform Class implementation.
     */

    public CryptoWalletManager getCryptoWalletManager() {
        return this.cryptoWalletManager;
    }

    public ErrorManager getErrorManager() {
        return this.errorManager;
    }



    /**
     *  Formationg balance amount
     * @param balance
     * @return
     */
    public static String formatBalanceString(long balance) {
        String stringBalance = "";

        switch (TYPE_AMOUNT_SELECTED){

            case TYPE_BTC:
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                df.setMinimumFractionDigits(2);
                String BTCFormat = df.format(balance / 100000000.0);
                stringBalance = BTCFormat + " BTC";
                break;
            case TYPE_BITS:
                stringBalance = (int) (balance / 100) + " bits";
                break;
        }
        return stringBalance;
    }


    public static String caluculateTimeAgo(long timeStamp) {

        long timeDiffernce;
        long unixTime = System.currentTimeMillis() / 1000L;  //get current time in seconds.
        int j;
        String[] periods = {"s", "m", "h", "d", "w", "m", "y", "d"};
        // you may choose to write full time intervals like seconds, minutes, days and so on
        double[] lengths = {60, 60, 24, 7, 4.35, 12, 10};
        timeDiffernce = unixTime - timeStamp;
        String tense = "ago";
        for (j = 0; timeDiffernce >= lengths[j] && j < lengths.length - 1; j++) {
            timeDiffernce /= lengths[j];
        }
        return timeDiffernce + periods[j] + " " + tense;




        }


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now =new Date().getTime(); //getCurrentTime(ctx);
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }


}
