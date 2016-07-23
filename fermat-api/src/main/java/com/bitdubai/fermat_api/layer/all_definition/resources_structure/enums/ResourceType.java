package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.enums.ResourceType</code>
 * enumerates type of Resources.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum ResourceType implements Serializable {
    VIDEO("video"),
    SOUND("sound"),
    IMAGE("image"),
    FONT_STYLE("font_style"),
    LAYOUT("layout");

    public static ResourceType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "video":
                return ResourceType.VIDEO;
            case "sound":
                return ResourceType.SOUND;
            case "image":
                return ResourceType.IMAGE;
            case "font_style":
                return ResourceType.FONT_STYLE;
            case "layout":
                return ResourceType.LAYOUT;
            //Modified by Manuel Perez on 04/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ResourceType enum");

        }

    }

    private final String code;

    ResourceType(String code) {
        this.code = code;
    }

    /**
     * Método no modificado ya que se usa en com/bitdubai/fermat_ccp_plugin/layer/middleware/wallet_factory/developer/bitdubai/version_1/structure/WalletFactoryMiddlewareProjectSkinManager.java
     */
    public String value() {
        return this.code;
    }
}
