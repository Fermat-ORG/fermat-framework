package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.sql.Date;
import java.util.List;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com) on 29/12/15.
 */
public interface ChatManager {
    void NewChat (Integer Id_Chat,Integer Id_Objeto,String Local_Actor_Type,
                  String Local_Actor_Pub_key,String Remote_Actor_Type,
                  String Remote_Actor_Pub_key, String Chat_Name,boolean Status,
                  Date Date, Date Last_Message_Date);

    List<Object> ChatList(Integer Id_Chat,Integer Id_Objeto);

    List<Object> ChatDetails(Integer Id_Chat,Integer Id_Objeto);

    List<Object> Send_Message(Integer Id_Message,Integer Id_Chat,String Message,
                         Integer Status,Boolean Type,Date Message_Date);


}
