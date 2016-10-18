package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.IntraUserDeleteContactEvent;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.WalletContactsMiddlewarePluginNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import java.util.UUID;

/**
 * Created by Gian Barboza on 29/08/16.
 */
public class IntraWalletUsersDeleteContactEventHandler implements FermatEventHandler<IntraUserDeleteContactEvent> {

    private final WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;

    private final WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot;

    public IntraWalletUsersDeleteContactEventHandler(WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry, final WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot) {
        this.walletContactsMiddlewareRegistry = walletContactsMiddlewareRegistry;
        this.walletContactsMiddlewarePluginRoot = walletContactsMiddlewarePluginRoot;
    }
    @Override
    public void handleEvent(IntraUserDeleteContactEvent fermatEvent) throws FermatException {
        if (this.walletContactsMiddlewarePluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof IntraUserDeleteContactEvent) {

                walletContactsMiddlewareRegistry.deleteWalletContactByActorPublicKey(fermatEvent.getActorPublicKey());

            } else {
                EventType eventExpected = EventType.INTRA_USER_WALLET_DELETE_CONTACT;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new WalletContactsMiddlewarePluginNotStartedException(null, "Plugin is not started.", "");
        }
    }
}
