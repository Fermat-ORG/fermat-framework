package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionContract;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public class IncomingConfirmBusinessTransactionContractEventHandler extends AbstractCloseContractEventHandler {

    ErrorManager errorManager;

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.closeContractRecorderService.getStatus() == ServiceStatus.STARTED) {

            try {
                this.closeContractRecorderService.incomingConfirmBusinessTransactionContractEventHandler((IncomingConfirmBusinessTransactionContract) fermatEvent);
            } catch (CantSaveEventException exception) {


                errorManager.reportUnexpectedPluginException(
                        Plugins.CLOSE_CONTRACT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantSaveEventException(exception, "Handling the IncomingConfirmBusinessTransactionContractEventHandler", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                errorManager.reportUnexpectedPluginException(
                        Plugins.CLOSE_CONTRACT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);

                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingConfirmBusinessTransactionContractEventHandler", "Cannot cast this event");
            } catch (Exception exception) {

                errorManager.reportUnexpectedPluginException(
                        Plugins.CLOSE_CONTRACT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantSaveEventException(exception, "Handling the IncomingConfirmBusinessTransactionContractEventHandler", "Unexpected exception");
            }

        } else {


            throw new TransactionServiceNotStartedException();
        }
    }
}
