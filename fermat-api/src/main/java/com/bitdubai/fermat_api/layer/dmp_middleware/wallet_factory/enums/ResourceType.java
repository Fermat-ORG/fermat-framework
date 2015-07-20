package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.enums.ResourceType</code>
 * enumerates type of Resources.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum ResourceType {
    VIDEO("video"),
    SOUND("sound"),
    IMAGE("images"),
    FONT_STYLE("font_style"),
    LAYOUT("layout");

    public static ResourceType fromValue(String key) throws InvalidParameterException {
        for (ResourceType type : ResourceType.values()) {
            if (type.value().equals(key))
                return type;
        }
        throw new InvalidParameterException(key);
    }

    private final String key;

    ResourceType(String key) {
        this.key = key;
    }

    public String value() { return this.key; }
}
