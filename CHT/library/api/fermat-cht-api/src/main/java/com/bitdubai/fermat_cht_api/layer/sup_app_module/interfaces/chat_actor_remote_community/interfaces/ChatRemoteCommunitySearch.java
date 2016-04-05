package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.interfaces;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception.CantGetChatRemoteSearchResult;
import java.util.List;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public interface ChatRemoteCommunitySearch {

    void addAlias(String alias);

    List<ChatRemoteCommunityInformation> getResult() throws CantGetChatRemoteSearchResult;
}
