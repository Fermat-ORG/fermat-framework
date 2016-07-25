package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cry_api.layer.definition.enums.AgentStatus</code>
 * Represents the different status of an agent.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/10/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum AgentStatus implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    CREATED("CREATED"),
    PAUSED("PAUSED"),
    STARTED("STARTED"),
    STOPPED("STOPPED");

    public final String code;

    AgentStatus(final String code) {
        this.code = code;
    }

    public static AgentStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "CREATED":
                return AgentStatus.CREATED;
            case "PAUSED":
                return AgentStatus.PAUSED;
            case "STARTED":
                return AgentStatus.STARTED;
            case "STOPPED":
                return AgentStatus.STOPPED;
            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the AgentStatus enum."
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
