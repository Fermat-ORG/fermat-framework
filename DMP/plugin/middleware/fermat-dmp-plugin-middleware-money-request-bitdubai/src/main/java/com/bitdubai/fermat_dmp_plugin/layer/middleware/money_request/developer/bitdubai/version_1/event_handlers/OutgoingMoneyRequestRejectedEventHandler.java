package com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_middleware.money_request.MoneyRequestManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
//import com.bitdubai.fermat_api.layer.crypto_module.actor_address_book.exceptions.ExampleException;

/**
 * Created by loui on 23/02/15.
 */
public class OutgoingMoneyRequestRejectedEventHandler implements FermatEventHandler {

    MoneyRequestManager moneyRequestManager;

    public void setMoneyRequestManager(MoneyRequestManager moneyRequestManager) {
        this.moneyRequestManager = moneyRequestManager;

    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.moneyRequestManager).getStatus() == ServiceStatus.STARTED) {
/*
            try
            {
                this.moneyRequestManager.exampleMethod();
            }
            catch (ExampleException exampleException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
            /*    System.err.println("CantCreateCryptoWalletException: "+ exampleException.getMessage());
                exampleException.printStackTrace();

                throw  exampleException;
            }
        }
        else
        {
            throw new MiddlewareNotStartedException();
        }
    }*/
        }
    }
}
