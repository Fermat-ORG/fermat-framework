package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.util.List;
import java.sql.Date;
/**
 * Created by miguel payarez (miguel_payarez@hotmail.com)  on 29/12/15.
 */
public interface ContactManager {

    List<Object> ContactDetail(Integer Id_Contact, String Remote_Name,String Alias,
                                String Remote_Actor_Type, String Remote_Actor_Pub_key,
                                Date Creation_Date);

    void ContactEdit(Integer Id_Contact, String Remote_Name,
                      String Remote_Actor_Type, String Remote_Actor_Pub_key);


    void DeleteContact(Integer Id_Contact, String Remote_Name,
                        String Remote_Actor_Type, String Remote_Actor_Pub_key);



}
