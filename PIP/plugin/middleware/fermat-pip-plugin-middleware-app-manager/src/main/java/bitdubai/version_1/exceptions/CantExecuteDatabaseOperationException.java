package bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/08/2015
 */
public class CantExecuteDatabaseOperationException extends FermatException {
    static final String DEFAULT_MESSAGE = "there was an error executing a database operation.";

    public CantExecuteDatabaseOperationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
