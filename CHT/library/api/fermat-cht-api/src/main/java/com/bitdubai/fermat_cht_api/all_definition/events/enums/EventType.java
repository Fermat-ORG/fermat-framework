package com.bitdubai.fermat_cht_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.events.ChatConnectionRequestNewEvent;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.events.ChatConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_cht_api.layer.middleware.event.IncomingChatMessageNotificationEvent;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewOnlineStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewWritingStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;

/** The enum <code>com.bitdubai.fermat_cht_api.fermat_chp_api.events.enums.EventType</code>
 * represent the different type of events found on cht platform.<p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    /**
     * ACTOR CONNECTION
     */
    CHAT_ACTOR_CONNECTION_REQUEST_NEW("CACRN") {
        public final FermatEvent getNewEvent() { return new ChatConnectionRequestNewEvent(this); }
    },
    CHAT_ACTOR_CONNECTION_UPDATE_CONNECTION("CACUC") {
        public final FermatEvent getNewEvent() { return new ChatConnectionRequestUpdatesEvent(this); }
    },

    /**
     * NETWORK SERVICES
     */
    OUTGOING_CHAT("OUTGCHAT"){
        public final FermatEvent getNewEvent() { return new OutgoingChat(this);}
    },
    INCOMING_CHAT("INCHAT"){
        public final FermatEvent getNewEvent()  {   return new IncomingChat(this);}
    },
    INCOMING_STATUS("INSTS"){
        public final FermatEvent getNewEvent()  { return new IncomingNewChatStatusUpdate(this);}
        },
    INCOMING_ONLINE_STATUS("INOSTS"){
        public final FermatEvent getNewEvent()  { return new IncomingNewOnlineStatusUpdate(this);}
    },
    INCOMING_WRITING_STATUS("INWSTS"){
        public final FermatEvent getNewEvent()  { return new IncomingNewWritingStatusUpdate(this);}
    },
    INCOMING_CHAT_MESSAGE_NOTIFICATION("INCCM"){
        public final FermatEvent getNewEvent() {  return new IncomingChatMessageNotificationEvent(this);}
    },
    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CHAT_PLATFORM;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override// by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
}
