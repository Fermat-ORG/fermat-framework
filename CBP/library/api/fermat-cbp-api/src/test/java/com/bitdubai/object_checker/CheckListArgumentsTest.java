package com.bitdubai.object_checker;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class CheckListArgumentsTest {

    @Test
    public void checkValidArgumentsTest() throws Exception {
        List<String> arguments = new ArrayList<>();
        arguments.add("Argument 0");
        arguments.add("Argument 1");
        arguments.add("Argument 2");
        ObjectChecker.checkArgument(arguments);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkInvalidArgumentsTest() throws Exception {
        List<String> arguments = new ArrayList<>();
        arguments.add("Argument 0");
        arguments.add(null);
        arguments.add("Argument 2");
        String exceptionMessage = "The argument 1 is null";
        thrown.expect(ObjectNotSetException.class);
        thrown.expectMessage(exceptionMessage);
        ObjectChecker.checkArguments(arguments);
    }

}
