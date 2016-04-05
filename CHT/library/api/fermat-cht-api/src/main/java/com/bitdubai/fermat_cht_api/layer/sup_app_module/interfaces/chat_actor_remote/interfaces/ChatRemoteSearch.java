package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.interfaces;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.CantGetChatRemoteSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.interfaces.ChatRemoteInformation;
import java.util.List;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */


public interface ChatRemoteSearch {

    void setNameToSearch(String nameToSearch);

    List<ChatRemoteInformation> getResult() throws CantGetChatRemoteSearchResult;
}
