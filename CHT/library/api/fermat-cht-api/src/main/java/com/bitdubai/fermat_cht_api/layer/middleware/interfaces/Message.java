package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public interface Message {
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
    Date getMessageDate();
    void setMessageDate(Date messageDate);
}
