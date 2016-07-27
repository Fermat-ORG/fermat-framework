package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public interface PluginDeveloperReferenceInterface {

    Developers getDeveloper();

    PluginReference getPluginReference();

    void setPluginReference(final PluginReference pluginReference);

    boolean equals(Object o);

    int hashCode();

    String toString();
}
