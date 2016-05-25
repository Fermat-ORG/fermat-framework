package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public interface Message extends Serializable {
    UUID  getMessageId();
    void setMessageId(UUID messageId);
    UUID getChatId();
    void setChatId(UUID chatId);
    String getMessage();
    void setMessage(String message);
    MessageStatus getStatus();
    void setStatus(MessageStatus status);
    TypeMessage getType();
    void setType(TypeMessage type);
    Timestamp getMessageDate();
    void setMessageDate(Timestamp messageDate);
    UUID getContactId();
    void setContactId(UUID contactId);
    long getCount();
    void setCount(long count);
}
