package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;

import java.util.UUID;

/**
 * Created by rodrigo on 8/13/15.
 */
public interface SkinResource {
    //getters
    UUID getId();
    String getName();
    String getFileName();
    ResourceType getResourceType();
    ResourceDensity getResourceDensity();

    //setters
    void setId(UUID id);
    void setName(String name);
    void setFileName(String fileName);
    void setResourceType(ResourceType resourceType);
    void setResourceDensity(ResourceDensity resourceDensity);
}
