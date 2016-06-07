package bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.AppManagerMiddlewareInstalledSkin</code>
 * is the implementation of InstalledSkin.
 * <p/>
 * <p/>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AppManagerMiddlewareInstalledSkin implements InstalledSkin {

    private String alias;
    private String preview;
    private UUID id;
    private Version version;

    /**
     * Constructor
     */
    public AppManagerMiddlewareInstalledSkin(UUID id, String alias, String preview, Version version) {
        this.alias = alias;
        this.preview = preview;
        this.id = id;
        this.version = version;
    }

    /**
     * InstalledSkin Interface implementation.
     */

    /**
     * This method gives us the name (alias) of an skin
     */
    @Override
    public String getAlias() {
        return this.alias;
    }

    /**
     * This method gives us the identifier of an skin
     */
    @Override
    public String getPreview() {
        return this.preview;
    }

    /**
     * This method gives us the name of the preview image of the skin
     */
    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * This method gives us the version of the skin
     */
    @Override
    public UUID getId() {
        return this.id;
    }
}
