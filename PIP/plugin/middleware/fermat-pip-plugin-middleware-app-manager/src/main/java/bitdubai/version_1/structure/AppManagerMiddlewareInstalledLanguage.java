package bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.AppManagerMiddlewareInstalledLanguage</code>
 * is the implementation of InstalledLanguage.
 * <p/>
 * <p/>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AppManagerMiddlewareInstalledLanguage implements InstalledLanguage {

    private Languages languages;
    private UUID id;
    private String label;
    private Version version;

    /**
     * Constructor
     */
    public AppManagerMiddlewareInstalledLanguage(UUID id, Languages languages, String label, Version version) {
        this.id = id;
        this.languages = languages;
        this.label = label;
        this.version = version;
    }

    /**
     * InstalledLanguage Interface implementation.
     */

    /**
     * This method gives us the language package identifier
     */
    @Override
    public UUID getId() {
        return this.id;
    }

    /**
     * This method gives us the language of the package
     */
    @Override
    public Languages getLanguage() {
        return this.languages;
    }

    /**
     * This method gives us the label of the language package. </p></>
     * E.g: the language could be english and the label UK.
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * This method gives us the version of the language package
     */
    @Override
    public Version getVersion() {
        return version;
    }
}
