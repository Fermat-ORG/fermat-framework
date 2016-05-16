package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.OutgoingMessage</code>
 * <p/>
 * Created  by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingMessagesDao extends AbstractBaseDao {

    public OutgoingMessagesDao(final Database dataBase) {

        super(
                dataBase                                ,
                null // TODO complete
        );
    }

    @Override
    protected NetworkServiceMessage getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {
        return null;
    }

    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(NetworkServiceMessage entity) {
        return null;
    }

}
