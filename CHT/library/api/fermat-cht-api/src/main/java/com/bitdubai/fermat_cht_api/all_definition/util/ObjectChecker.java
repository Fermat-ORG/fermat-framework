package com.bitdubai.fermat_cht_api.all_definition.util;


import com.bitdubai.fermat_cht_api.all_definition.exceptions.ObjectNotSetException;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class ObjectChecker {

    /**
     * This method checks if an argument is null.
     *
     * @param argument
     * @throws ObjectNotSetException
     */
    public static void checkArgument(
            Object argument
    ) throws ObjectNotSetException {
        checkArgument(argument, "The argument is null");
    }

    /**
     * This method checks if an argument is null, also, can sets the exception message.
     *
     * @param argument
     * @param exceptionMessage
     * @throws ObjectNotSetException
     */
    public static void checkArgument(
            Object argument,
            String exceptionMessage) throws
            ObjectNotSetException {
        if (argument == null) {
            throw new ObjectNotSetException(exceptionMessage);
        }
    }

    /**
     * This method checks an argument List that is required to check if has a valid value.
     * In this case, checks if the argument is not null.
     *
     * @param checkingArguments
     * @throws ObjectNotSetException
     */
    public static void checkArguments(
            List checkingArguments) throws
            ObjectNotSetException {
        int argumentCounter = 0;
        for (Object argument : checkingArguments) {
            checkArgument(argument, new StringBuilder().append("The argument ").append(argumentCounter).append(" is null").toString());
            argumentCounter++;
        }

    }

    /**
     * This method checks an argument array that is required to check if has a valid value.
     * In this case, checks if the argument is not null.
     *
     * @param checkingArguments
     * @throws ObjectNotSetException
     */
    public static void checkArguments(
            Object[] checkingArguments) throws
            ObjectNotSetException {

        int argumentCounter = 0;
        for (Object argument : checkingArguments) {
            checkArgument(argument, new StringBuilder().append("The argument ").append(argumentCounter).append(" is null").toString());
            argumentCounter++;
        }

    }

}
