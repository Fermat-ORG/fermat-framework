package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.IntraUserUpdateContactEvent;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.WalletContactsMiddlewarePluginNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import java.util.UUID;

/**
 * Created by Gian Barboza on 30/08/16.
 */
public class IntraWalletUsersUpdateContactEventHandler implements FermatEventHandler<IntraUserUpdateContactEvent>{

    private final WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;

    private final WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot;

    public IntraWalletUsersUpdateContactEventHandler(WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry, final WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot) {
        this.walletContactsMiddlewareRegistry = walletContactsMiddlewareRegistry;
        this.walletContactsMiddlewarePluginRoot = walletContactsMiddlewarePluginRoot;
    }

    @Override
    public void handleEvent(IntraUserUpdateContactEvent fermatEvent) throws FermatException {
        if (this.walletContactsMiddlewarePluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof IntraUserUpdateContactEvent) {

                walletContactsMiddlewareRegistry.updateWalletContactByActorPublicKey(
                        fermatEvent.getIdentityPublicKey(),
                        fermatEvent.getIdentityAlias());

            } else {
                EventType eventExpected = EventType.INTRA_USER_WALLET_UPDATE_CONTACT;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new WalletContactsMiddlewarePluginNotStartedException(null, "Plugin is not started.", "");
        }
    }
}
