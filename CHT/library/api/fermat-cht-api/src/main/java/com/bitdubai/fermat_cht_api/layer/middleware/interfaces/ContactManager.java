package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.util.List;
import java.sql.Date;
import java.util.UUID;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com)  on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface ContactManager {

    //Todo: create a contact record or contact wrapper to clean this method
    //Documentar
    //Please, check the observations on ChatManager
    List<Contact> getContacts();

    Contact getContactByContactId(UUID contactId);

    Contact newEmptyInstanceContact();

    void saveContact(Contact contact);

    void deleteContact(Contact contact);
}
