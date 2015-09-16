package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.interfaces.DiscountWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.discount_wallet.exceptions.DiscountWalletManagerServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletCreatedEvent;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public class WalletCreatedEventHandler implements FermatEventHandler {

    DiscountWalletManager discountWalletManager;

    public void setWalletmanager ( DiscountWalletManager discountWalletManager){
        this.discountWalletManager = discountWalletManager;
    }

    @Override
    public  void handleEvent(FermatEvent fermatEvent) throws FermatException {
        UUID walletId = ((WalletCreatedEvent) fermatEvent).getWalletId();
        FiatCurrency fiatCurrency= ((WalletCreatedEvent) fermatEvent).getFiatCurrency();
        CryptoCurrency cryptoCurrency = ((WalletCreatedEvent) fermatEvent).getCryptoCurrency();

        /*
         * This event is thrown by different plug-ins, in particular by us.
         * We want to react to it if the plug-in that threw it is not us.
         * So we check if the source is our plug-in and exit if so.
         * TODo: Write a real specification to see if there is other source we want to ignore
        */
        if(fermatEvent.getSource() == EventSource.DISCOUNT_WALLET_BASIC_WALLET_PLUGIN)
            return;


        if (((Service) this.discountWalletManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.discountWalletManager.createWallet(walletId,fiatCurrency,cryptoCurrency);
            }
            catch (CantCreateWalletException cantCreateWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: "+ cantCreateWalletException.getMessage());
                cantCreateWalletException.printStackTrace();

                throw  cantCreateWalletException;
            }
        }
        else
        {
            throw new DiscountWalletManagerServiceNotStartedException();
        }
    }
    
}
