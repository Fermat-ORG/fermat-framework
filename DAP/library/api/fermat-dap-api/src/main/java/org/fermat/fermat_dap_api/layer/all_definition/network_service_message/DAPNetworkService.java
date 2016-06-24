package org.fermat.fermat_dap_api.layer.all_definition.network_service_message;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendDAPMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;

import java.util.List;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public interface DAPNetworkService {
    /**
     * @param dapMessage the message to be sent, this message has to contain both the actor
     *                   that sent the message and the actor that will receive the message.
     * @throws CantSendDAPMessageException
     */
    void sendMessage(DAPMessage dapMessage) throws CantSendDAPMessageException;

    /**
     * This method retrieves the list of new incoming and unread DAP Messages for a specific type.
     *
     * @param type The {@link DAPMessageType} of message to search for.
     * @return {@link List} instance filled with all the {@link DAPMessage} that were found.
     * @throws CantGetDAPMessagesException If there was an error while querying for the list.
     */
    List<DAPMessage> getUnreadDAPMessagesByType(DAPMessageType type) throws CantGetDAPMessagesException;

    /**
     * This method returns the list of new unread DAPMessages for a specific subject, these messages can be
     * from the same or different types.
     *
     * @param subject
     * @return
     * @throws CantGetDAPMessagesException
     */
    List<DAPMessage> getUnreadDAPMessageBySubject(DAPMessageSubject subject) throws CantGetDAPMessagesException;

    void confirmReception(DAPMessage message) throws CantUpdateMessageStatusException;
}
