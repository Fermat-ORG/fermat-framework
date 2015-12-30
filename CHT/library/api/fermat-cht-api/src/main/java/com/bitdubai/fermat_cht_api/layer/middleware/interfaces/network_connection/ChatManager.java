package com.bitdubai.fermat_cht_api.layer.middleware.interfaces.network_connection;

/**
 * Created by miguel payarez on 29/12/15.
 */

import java.sql.Date;
public interface ChatManager {

    void event_recive_message(Integer Id_Message,Integer Id_Chat, String Message_Text,
                              Integer Status,Date Message_Date);

    void event_send_message(Integer Id_Message,Integer Id_Chat,
                                  Integer Status,Date Message_Date);

    void event_read_mesage(Integer Id_Message,Integer Id_Chat);

    void event_read_message_notification(Integer Id_Message,Integer Id_Chat);

}