package com.bitdubai.fermat_api.layer.all_definition.developer;

/**
 * Created by ciencias on 6/25/15.
 */
public interface LoggingManagerForDevelopers {

    public LoggingStatus getLoggingStatus();

    public void changeLoggingStatus(LoggingStatus newLoggingStatus);

}
