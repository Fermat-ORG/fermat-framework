package com.bitdubai.object_checker;


import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class CheckArgumentTest {

    @Test
    public void checkValidArgumentTest() throws Exception {
        String argument = "Valid Argument";
        ObjectChecker.checkArgument(argument);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkInvalidArgumentTest() throws Exception {
        String argument = null;
        thrown.expect(ObjectNotSetException.class);
        thrown.expectMessage("The argument is null");
        ObjectChecker.checkArgument(argument);
    }

}
