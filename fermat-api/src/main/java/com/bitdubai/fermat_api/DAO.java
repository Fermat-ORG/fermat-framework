package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseAggregateFunction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantTruncateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseRecordExistException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * Created by Natalia on 09/02/2015..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 * Modified by Matias Furszyfer
 */

/**
 * DAO base
 * Created by Matias Furszyfer
 */

public abstract class DAO{

    private Database dataBase;
    private String tableName;
    //todo: este field no debe estar
    private DatabaseTable table;

    public DAO(Database dataBase, String tableName) {
        this.dataBase = dataBase;
        this.tableName = tableName;
        this.table = dataBase.getTable(tableName);
    }


    /**
     * DatabaseTable interface implementation.
     */

    abstract String[] columnNames();

    /**
     * <p>This method return a list of Database Table Record objects
     *
     * @return List<DatabaseTableRecord> List of DatabaseTableRecord objects
     */
    public List<DatabaseTableRecord> getRecords(List<DatabaseTableFilter>filters,List<DatabaseTableFilterGroup> groups,String[] columns) throws CantLoadTableToMemoryException {
        return table.loadRecords(filters,groups,columns);
    }
    public List<DatabaseTableRecord> getRecords(List<DatabaseTableFilter>filters,List<DatabaseTableFilterGroup> groups) throws CantLoadTableToMemoryException {
        return table.loadRecords(filters, groups, columnNames());
    }
    public List<DatabaseTableRecord> getRecords(List<DatabaseTableFilter>filters) throws CantLoadTableToMemoryException {
        return getRecords(filters, null);
    }
    /**
     * <p>This method update a table record in the database
     *
     * @param record DatabaseTableRecord object to update
     * @throws CantUpdateRecordException
     */
    //todo: esto tiene que estar mejor pero no me da la cabeza hoy
    public void updateRecord(DatabaseTableRecord record,List<DatabaseTableFilter> filters,List<DatabaseTableFilterGroup> databaseTableFilterGroups) throws CantUpdateRecordException {
        DatabaseTable databaseTable = dataBase.getTable(tableName);
        table.updateRecord(record);
    }

}

