package com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.AddressBookManager;

/**
 * Created by loui on 22/02/15.
 */
public class IncomingCryptoReceptionConfimedEventHandler implements EventHandler {
    AddressBookManager addressBookManager;

    public void setAddressBookManager(AddressBookManager addressBookManager){
        this.addressBookManager = addressBookManager;
    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

    }
}
