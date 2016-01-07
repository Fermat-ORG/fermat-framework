package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;

/**
 * Created by root on 05/01/16.
 */
public interface ChatMessageMetada {

    ChatMessageStatus getChatMessageStatus();

    /**
     * Get the Receiver Id
     *
     * @return String
     */
    String getReceiverId();

    /**
     * The platform component that this event is destinated for.
     * @return {@link PlatformComponentType}
     */
    PlatformComponentType getReceiverType();
    /**
     * Get the Sender Id
     *
     * @return String
     */
    String getSenderId();

    /**
     * The platform component that send this event.
     * @return {@link PlatformComponentType}
     */
    PlatformComponentType getSenderType();
}
