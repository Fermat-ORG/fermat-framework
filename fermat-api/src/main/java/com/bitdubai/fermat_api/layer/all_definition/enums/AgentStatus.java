package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cry_api.layer.definition.enums.AgentStatus</code>
 * represent the different status of an agent.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum AgentStatus implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    CREATED("CREATED"),
    PAUSED("PAUSED"),
    STARTED("STARTED"),
    STOPPED("STOPPED");

    public String code;

    AgentStatus(String code) {
        this.code = code;
    }

    public static AgentStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "CREATED": return AgentStatus.CREATED;
            case "PAUSED":  return AgentStatus.PAUSED;
            case "STARTED": return AgentStatus.STARTED;
            case "STOPPED": return AgentStatus.STOPPED;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the AgentStatus enum."
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
