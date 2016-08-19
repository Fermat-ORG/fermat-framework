package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface ChatMetadata extends MetadataToSend {

    UUID getChatId();

    UUID getObjectId();

    String getChatName();

    ChatMessageStatus getChatMessageStatus();

    TypeChat getTypeChat();

    List<GroupMember> getGroupMembers();

}
