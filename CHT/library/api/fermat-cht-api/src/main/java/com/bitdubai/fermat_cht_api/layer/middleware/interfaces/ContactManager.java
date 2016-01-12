package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;

import java.util.List;
import java.sql.Date;
import java.util.UUID;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com)  on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface ContactManager {
    //TODO: Documentar

    List<Contact> getContacts() throws CantGetContactException;

    Contact getContactByContactId(UUID contactId) throws CantGetContactException;

    Contact newEmptyInstanceContact() throws CantNewEmptyContactException;

    void saveContact(Contact contact) throws CantSaveContactException;

    void deleteContact(Contact contact) throws CantDeleteContactException;
}
