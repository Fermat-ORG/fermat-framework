package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 4/3/15.
 * Modified by Federico Rodriguez on 01.05.15
 */


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase maneja un archivo con las excepciones recibidas x cada developer. Siempre agrega informacion al archivo.
 * No mantiene el arvhico abierto porque no sabe la frecuencia con la que recibira nuevas excepciones y ademas, porque
 * el Report Agent puede llegar a renombrarlo una vez que logre transmitirlo al developer.
 * <p/>
 * Entonces en el fondo, lo que maneja son los archivos aun no transmitidos.
 * <p/>
 * <p/>
 * Tambien maneja un segundo archivo donde lista el plugin, la severidad declarada , para que el agente decida en base a
 * eso cuando debe transmitir la informacion al Developer.
 * * *
 * * * * *
 * * * * * *
 */
public class ErrorManagerRegistry {

    private long id;
    private String componentType;
    private String componentName;
    private String severity;
    private String exceptionMessage;
    private long sent;
    private long timeStampMillis;
    private PlatformDatabaseSystem platformDatabaseSystem;
    private Database errorManagerDB;

    public ErrorManagerRegistry() {
    }

    public ErrorManagerRegistry(String value) {
        if (value == null)
            throw new IllegalArgumentException();

    }

    /*
    * Getters and Setters
     */

    public PlatformDatabaseSystem getPlatformDatabaseSystem() {
        return platformDatabaseSystem;
    }

    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public long getSent() {
        return sent;
    }

    public void setSent(long sent) {
        this.sent = sent;
    }

    public long getTimeStampMillis() {
        return timeStampMillis;
    }

    public void setTimeStampMillis(long timeStampMillis) {
        this.timeStampMillis = timeStampMillis;
    }

    /*
    * Methods
    */

    public void initialize() throws FermatException {
        try {
            this.errorManagerDB = this.platformDatabaseSystem.openDatabase(ErrorManagerDatabaseConstants.EXCEPTION_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            ErrorManagerDatabaseFactory databasefactory = new ErrorManagerDatabaseFactory();
            databasefactory.setPlatformDatabaseSystem(this.platformDatabaseSystem);

            try {
                this.errorManagerDB = databasefactory.createErrorManagerDatabase();
            } catch (CantCreateDatabaseException e1) {
                throw new RuntimeException();
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new FermatException(FermatException.DEFAULT_MESSAGE, cantOpenDatabaseException, "This is a bad design decision", "We shouldn't depend on the creation of this database");
        }
    }


    public void createNewErrorRegistry(String componentType, String componentName, String severity,
                                       String exceptionMessage, long sent, long timeStampMillis) {
        this.componentType = componentType;
        this.componentName = componentName;
        this.severity = severity;
        this.exceptionMessage = exceptionMessage;
        this.sent = sent;
        this.timeStampMillis = timeStampMillis;
        this.addNewErrorRegistry();
    }


    /*
    * Adds a new ErrorRegistry into the database
    */
    public void addNewErrorRegistry() {
        //TODO: how is this supposed to work???????
        DatabaseTableRecord errorManagerRegistryRecord = (DatabaseTableRecord) this;
        try {
            errorManagerDB.getTable(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_NAME).insertRecord(errorManagerRegistryRecord);
        } catch (CantInsertRecordException cantInsertRecord) {
            cantInsertRecord.printStackTrace();
        }
    }

    /*
    * Marks an ErrorRegistry as Sent [1] and updates the registry of the database
    */
    public void markErrorRegistryAsSent() {
        this.sent = 1L;
        DatabaseTableRecord errorManagerRegistryRecord = (DatabaseTableRecord) this;
        try {
            errorManagerDB.getTable(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_NAME).updateRecord(errorManagerRegistryRecord);
        } catch (CantUpdateRecordException cantUpdateRecord) {
            cantUpdateRecord.printStackTrace();
        }
    }

    /*
    * Gets a List of ErrorRegistry from the database with the flag Sent equals to 0
    */
    public List getListOfErrorRegistryNotSent() {
        List<DatabaseTableRecord> emRecords = errorManagerDB.getTable(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_NAME).getRecords();
        List<ErrorManagerRegistry> listErrorRegistryNotSent = new LinkedList<ErrorManagerRegistry>();
        for (DatabaseTableRecord dbr : emRecords) {
            if (dbr.getLongValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_SENT_COLUMN_NAME) == 0L) {
                ErrorManagerRegistry emr = new ErrorManagerRegistry();
                emr.createNewErrorRegistry(
                        dbr.getStringValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_COMPONENT_TYPE_COLUMN_NAME),
                        dbr.getStringValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_COMPONENT_NAME_COLUMN_NAME),
                        dbr.getStringValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_SEVERITY_COLUMN_NAME),
                        dbr.getStringValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_MESSAGE_COLUMN_NAME),
                        dbr.getLongValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_SENT_COLUMN_NAME),
                        dbr.getLongValue(ErrorManagerDatabaseConstants.EXCEPTION_TABLE_TIMESTAMP_COLUMN_NAME)
                );
                listErrorRegistryNotSent.add(emr);
            }
        }
        return listErrorRegistryNotSent;
    }


}
