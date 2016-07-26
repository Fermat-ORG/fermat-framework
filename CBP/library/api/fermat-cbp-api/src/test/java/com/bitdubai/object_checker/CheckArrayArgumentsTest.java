package com.bitdubai.object_checker;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class CheckArrayArgumentsTest {

    @Test
    public void checkValidArgumentsTest() throws Exception {
        String[] arguments = {"Argument 0", "Argument 1", "Argument 2"};
        ObjectChecker.checkArgument(arguments);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkInvalidArgumentsTest() throws Exception {
        String[] arguments = {"Argument 0", null, "Argument 2"};
        String exceptionMessage = "The argument 1 is null";
        thrown.expect(ObjectNotSetException.class);
        thrown.expectMessage(exceptionMessage);
        ObjectChecker.checkArguments(arguments);
    }

}
