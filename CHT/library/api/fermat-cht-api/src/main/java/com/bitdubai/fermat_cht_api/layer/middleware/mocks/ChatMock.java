package com.bitdubai.fermat_cht_api.layer.middleware.mocks;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;

import java.sql.Date;
import java.util.UUID;

/**
 * This class is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class ChatMock implements Chat {
    @Override
    public UUID getChatId() {
        return UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f");
    }

    @Override
    public void setChatId(UUID chatId) {

    }

    @Override
    public UUID getObjectId() {
        return UUID.fromString("f85ac4bf-4cb7-4c07-b923-2edd3efef02c");
    }

    @Override
    public void setObjectId(UUID objectId) {

    }

    @Override
    public PlatformComponentType getLocalActorType() {
        return PlatformComponentType.NETWORK_SERVICE;
    }

    @Override
    public void setLocalActorType(PlatformComponentType localActorType) {

    }

    @Override
    public String getLocalActorPublicKey() {
        return "TestLocalActorPublicKey";
    }

    @Override
    public void setLocalActorPublicKey(String localActorPublicKey) {

    }

    @Override
    public PlatformComponentType getRemoteActorType() {
        return PlatformComponentType.NETWORK_SERVICE;
    }

    @Override
    public void setRemoteActorType(PlatformComponentType remoteActorType) {

    }

    @Override
    public String getRemoteActorPublicKey() {
        return "RemoteActorPublicKey";
    }

    @Override
    public void setRemoteActorPublicKey(String remoteActorPublicKey) {

    }

    @Override
    public String getChatName() {
        return "Evil Chat";
    }

    @Override
    public void setChatName(String chatName) {

    }

    @Override
    public ChatStatus getStatus() {
        return ChatStatus.VISSIBLE;
    }

    @Override
    public void setStatus(ChatStatus status) {

    }

    @Override
    public Date getDate() {
        return new Date(2001);
    }

    @Override
    public void setDate(Date date) {

    }

    @Override
    public Date getLastMessageDate() {
        return new Date(2001);
    }

    @Override
    public void setLastMessageDate(Date lastMessageDate) {

    }
}
