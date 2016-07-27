package com.bitdubai.object_checker;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class CheckArgumentWithExceptionMessageTest {

    @Test
    public void checkValidArgumentWithExceptionMessageTest() throws Exception {
        String argument = "Valid Argument";
        ObjectChecker.checkArgument(argument, "The argument is null");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkInvalidArgumentWithExceptionMessageTest() throws Exception {
        String argument = null;
        String exceptionMessage = "The argument is null";
        thrown.expect(ObjectNotSetException.class);
        thrown.expectMessage(exceptionMessage);
        ObjectChecker.checkArgument(argument, exceptionMessage);
    }

}
