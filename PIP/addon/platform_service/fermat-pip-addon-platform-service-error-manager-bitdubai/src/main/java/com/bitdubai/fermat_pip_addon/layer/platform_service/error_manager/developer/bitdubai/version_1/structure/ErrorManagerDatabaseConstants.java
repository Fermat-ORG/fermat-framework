package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure;

/**
 * Created by federico matias rodriguez on 26/04/15.
 * Modified by Federico Rodriguez on 29.4.15
 */
public class ErrorManagerDatabaseConstants {

    /**
     * ErrorManager exception report database table definition.
     */
    public static final String EXCEPTION_DATABASE_NAME = "ErrorManagerExceptionDatabase";
    public static final String EXCEPTION_TABLE_NAME = "ErrorManagerPluginExceptions";
    public static final String EXCEPTION_TABLE_ID_COLUMN_NAME = "Id";
    public static final String EXCEPTION_TABLE_COMPONENT_TYPE_COLUMN_NAME = "ComponentType";
    public static final String EXCEPTION_TABLE_COMPONENT_NAME_COLUMN_NAME = "Name";
    public static final String EXCEPTION_TABLE_SEVERITY_COLUMN_NAME = "ExceptionSeverity";
    public static final String EXCEPTION_TABLE_MESSAGE_COLUMN_NAME = "ExceptionMessage";
    public static final String EXCEPTION_TABLE_SENT_COLUMN_NAME = "Send";
    public static final String EXCEPTION_TABLE_TIMESTAMP_COLUMN_NAME = "TimeStamp";

}
