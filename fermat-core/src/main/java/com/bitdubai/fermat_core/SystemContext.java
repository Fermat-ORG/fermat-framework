package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.PlatformNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_core.SystemContext</code>
 * the system context hold all the  references of the mains components of fermat.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public class SystemContext {

    private final Map<Platforms, AbstractPlatform> platforms;

    public SystemContext() {

        platforms = new ConcurrentHashMap<>();
    }

    /**
     * Throw the method <code>registerLayer</code> you can add new layers to the platform.
     * Here we'll corroborate too that the layer is not added twice.
     *
     * @param abstractPlatform  platform instance.
     *
     * @throws CantRegisterPlatformException if something goes wrong.
     */
    public final void registerPlatform(AbstractPlatform abstractPlatform) throws CantRegisterPlatformException {

        Platforms platformEnum = abstractPlatform.getPlatformEnum();

        try {

            if(platforms.containsKey(platformEnum))
                throw new CantRegisterPlatformException("platform: " + platformEnum.toString(), "platform already exists in the system context.");

            abstractPlatform.start();

            platforms.put(
                    platformEnum,
                    abstractPlatform
            );

        } catch (final CantStartPlatformException e) {

            throw new CantRegisterPlatformException(e, "platform: " + platformEnum.toString(), "Error trying to start the platform.");
        }
    }

    public final AbstractPlatform getPlatform(Platforms platformEnum) throws PlatformNotFoundException {

        if (platforms.containsKey(platformEnum)) {
            return platforms.get(platformEnum);
        } else {
            throw new PlatformNotFoundException(
                    "platformEnum: " + platformEnum.toString(),
                    "platform not found in the system context."
            );
        }
    }

}
