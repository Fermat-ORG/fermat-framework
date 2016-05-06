package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactListException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 * pdated by Manuel Perez on 08/02/2016
 */
public class ChatImpl implements Chat {
    //TODO: Documentar
    private UUID                        chatId;
    private UUID                        objectId;
    private PlatformComponentType       localActorType;
    private String                      localActorPublicKey;
    private PlatformComponentType       remoteActorType;
    private String                      remoteActorPublicKey;
    private String                      chatName;
    private ChatStatus                  status;
    private Timestamp                   date;
    private Timestamp                   lastMessageDate;
    private List<Contact>               contactAssociated;
    private TypeChat                    typeChat;
    private boolean                     scheduledDelivery;
    private boolean                     isWriting;
    private boolean                     isOnline;

    /**
     * Constructor without arguments
     */
    public ChatImpl(){}

    /**
     * Construct with parameters. In this version we not included the List<Contact> contactAssociated
     * as a constructor argument because we get the contact from a remote device, this parameter
     * can be set later.
     * @param chatId
     * @param objectId
     * @param localActorType
     * @param localActorPublicKey
     * @param remoteActorType
     * @param remoteActorPublicKey
     * @param chatName
     * @param status
     * @param date
     * @param lastMessageDate
     */
    public ChatImpl(UUID chatId,
                    UUID objectId,
                    PlatformComponentType localActorType,
                    String localActorPublicKey,
                    PlatformComponentType remoteActorType,
                    String remoteActorPublicKey,
                    String chatName,
                    ChatStatus status,
                    Timestamp date,
                    Timestamp lastMessageDate,
                    TypeChat typeChat,
                    boolean scheduledDelivery
    )
    {
        this.chatId               = chatId;
        this.objectId             = objectId;
        this.localActorType       = localActorType;
        this.localActorPublicKey  = localActorPublicKey;
        this.remoteActorType      = remoteActorType;
        this.remoteActorPublicKey = remoteActorPublicKey;
        this.chatName             = chatName;
        this.status               = status;
        this.date                 = date;
        this.lastMessageDate      = lastMessageDate;
        this.typeChat             = typeChat;
        this.scheduledDelivery = scheduledDelivery;
    }

    @Override
    public UUID getChatId() {
        return chatId;
    }

    @Override
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public UUID getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    @Override
    public PlatformComponentType getLocalActorType() {
        return localActorType;
    }

    @Override
    public void setLocalActorType(PlatformComponentType localActorType) {
        this.localActorType = localActorType;
    }

    @Override
    public String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    @Override
    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public PlatformComponentType getRemoteActorType() {
        return this.remoteActorType;
    }

    @Override
    public void setRemoteActorType(PlatformComponentType remoteActorType) {
        this.remoteActorType = remoteActorType;
    }

    @Override
    public String getRemoteActorPublicKey() {
        return this.remoteActorPublicKey;
    }

    @Override
    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
    }

    @Override
    public String getChatName() {
        return this.chatName;
    }

    @Override
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @Override
    public ChatStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(ChatStatus status) {
        this.status = status;
    }

    @Override
    public Timestamp getDate() {
        return this.date;
    }

    @Override
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public Timestamp getLastMessageDate() {
        return this.lastMessageDate;
    }

    @Override
    public void setLastMessageDate(Timestamp lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    /**
     * This method returns a List<Contact> associated to this chat
     * @return
     */
    @Override
    public List<Contact> getContactAssociated() {
        return this.contactAssociated;
    }

    /**
     * This method set the contact associated list
     * @param chatContacts
     */
    @Override
    public void setContactAssociated(List<Contact> chatContacts) {
        this.contactAssociated=chatContacts;
    }

    /**
     * This method set one contact in contact associated list
     * @param contact
     */
    @Override
    public void setContactAssociated(Contact contact) {
        if(this.contactAssociated==null){
            this.contactAssociated=new ArrayList<>();
        }
        this.contactAssociated.add(contact);
    }

    /**
     * This method returns a XML String with the List<Contact> associated to this object
     * @return
     */
    @Override
    public String getContactListString() {
        if(this.contactAssociated==null){
            this.contactAssociated=new ArrayList<>();
        }
        return XMLParser.parseObject(this.contactAssociated);
    }

    /**
     * This method requires a valid List<Contact> XML String to set this list to this object
     * @param chatContacts
     * @throws CantGetContactListException
     */
    public void setContactAssociated(String chatContacts) throws CantGetContactListException {
        if(chatContacts==null||chatContacts.isEmpty()){
            throw new CantGetContactListException("The XML with the contacts is null or empty");
        }
        try{
            List<Contact> contactListFromXML=new ArrayList<>();
            Object xmlObject =XMLParser.parseXML(chatContacts, contactListFromXML);
            contactListFromXML=(List<Contact>) xmlObject;
            this.contactAssociated=contactListFromXML;
        } catch (Exception exception){
            throw new CantGetContactListException(
                    exception,
                    "Parsing the XML String to a List<Contact>",
                    "Unexpected exception");
        }

    }

    @Override
    public List<Message> getMessagesAsociated() {
        return null;
    }

    @Override
    public void setMessagesAsociated(List<Message> messages) {

    }

    @Override
    public TypeChat getTypeChat() {
        return typeChat;
    }

    @Override
    public void setTypeChat(TypeChat typeChat) {
        this.typeChat = typeChat;
    }


    @Override
    public List<GroupMember> getGroupMembersAssociated() {
        return null;
    }

    @Override
    public void setGroupMembersAssociated(List<GroupMember> groupMembers) {

    }

    @Override
    public boolean getScheduledDelivery() {
        return scheduledDelivery;
    }

    public boolean isWriting() {
        return isWriting;
    }

    public void setIsWriting(boolean isWriting) {
        this.isWriting = isWriting;
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public void setScheduledDelivery(boolean scheduledDelivery) {

        this.scheduledDelivery = scheduledDelivery;
    }

    @Override
    public String toString() {
        return "ChatImpl{" +
                "chatId=" + chatId +
                ", objectId=" + objectId +
                ", localActorType=" + localActorType +
                ", localActorPublicKey='" + localActorPublicKey + '\'' +
                ", remoteActorType=" + remoteActorType +
                ", remoteActorPublicKey='" + remoteActorPublicKey + '\'' +
                ", chatName='" + chatName + '\'' +
                ", status=" + status +
                ", date=" + date +
                ", lastMessageDate=" + lastMessageDate +
                ", contactAssociated=" + contactAssociated +
                '}';
    }
}
