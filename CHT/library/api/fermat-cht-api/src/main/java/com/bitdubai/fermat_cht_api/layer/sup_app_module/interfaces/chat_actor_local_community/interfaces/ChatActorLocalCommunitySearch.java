package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.interfaces;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantGetChtActorLocalSearchResult;

import java.util.List;

/**
 * Created by Eleazar Oroño (eorono@protonmail.com) on 1/04/16.
 */
public interface ChatActorLocalCommunitySearch {

    void addLocalActorAlias(String alias);

    List<ChtActorLocalCommunityInformation> getResult() throws CantGetChtActorLocalSearchResult;

}
