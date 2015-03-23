package com.bitdubai.fermat_api.layer._2_os.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;

/**
 * Created by ciencias on 3/23/15.
 */
public interface DatabaseFactory {

    public void createTable(UUID ownerId) throws InvalidOwnerId;

    public DatabaseTable newTable(UUID ownerId, String tableName) throws InvalidOwnerId;

}
