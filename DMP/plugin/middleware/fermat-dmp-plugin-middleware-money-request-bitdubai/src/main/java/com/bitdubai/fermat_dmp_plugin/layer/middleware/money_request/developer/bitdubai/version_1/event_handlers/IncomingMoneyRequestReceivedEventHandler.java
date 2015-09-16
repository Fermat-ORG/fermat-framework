package com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_middleware.MiddlewareNotStartedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.money_request.MoneyRequestManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingMoneyRequestReceivedEventHandler implements FermatEventHandler {

    MoneyRequestManager moneyRequestManager;

    public void setMoneyRequestManager(MoneyRequestManager moneyRequestManager){
        this.moneyRequestManager = moneyRequestManager;

    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.moneyRequestManager).getStatus() == ServiceStatus.STARTED){


        }
        else
        {
            throw new MiddlewareNotStartedException();
        }
    }
}
