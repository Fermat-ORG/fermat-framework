package com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.actor_connection.common.database.ActorConnectionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * This class contains all the basic constants for an actor connection plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class ActorConnectionDatabaseConstants {

    public static final String ACTOR_CONNECTION_DATABASE_NAME                           = "actor_connection"          ;

    /**
     * Actor Connections database table definition.
     */
    public static final String ACTOR_CONNECTIONS_TABLE_NAME                             = "actor_connections"         ;

    public static final String ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME              = "connection_id"             ;
    public static final String ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "linked_identity_public_key";
    public static final String ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME = "linked_identity_actor_type";
    public static final String ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME                 = "public_key"                ;
    public static final String ACTOR_CONNECTIONS_ACTOR_TYPE_COLUMN_NAME                 = "actor_type"                ;
    public static final String ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME                      = "alias"                     ;
    public static final String ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME           = "connection_state"          ;
    public static final String ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME              = "creation_time"             ;
    public static final String ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME                = "update_time"               ;

    public static final String ACTOR_CONNECTIONS_FIRST_KEY_COLUMN                       = "connection_id"             ;

}