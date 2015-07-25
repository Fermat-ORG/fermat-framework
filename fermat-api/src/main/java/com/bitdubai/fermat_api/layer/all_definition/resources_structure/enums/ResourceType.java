package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

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
    IMAGE("image"),
    FONT_STYLE("font_style"),
    LAYOUT("layout");

    public static ResourceType fromValue(String key) throws InvalidParameterException {
        switch(key){
            case "video":
                return ResourceType.VIDEO ;
            case "sound":
                return ResourceType.SOUND ;
            case "image":
                return ResourceType.IMAGE ;
            case "font_style":
                return ResourceType.FONT_STYLE;
            case "layout":
                return ResourceType.LAYOUT;
        }
        throw new InvalidParameterException(key);
    }

    private final String key;

    ResourceType(String key) {
        this.key = key;
    }

    public String value() { return this.key; }
}
