package com.bitdubai.fermat_core.layer._5_user.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_api.layer._5_user.extra_user.UserRegistry;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public class ExtraUserRegistry implements UserRegistry {


    /**
     * La estructura de base de datos que tenes que usar aca es muy simple, solo una tabla:
     * 
     * Tabla: UserRegistry
     * 
     * Campos: 
     * 
     * Id
     * Name
     * TimeStamp
     * 
     * Indice x Id
     *
     * 
     * * * * * * * * * * * * * * * * * * * 
     */
    
    
    @Override
    public User createUser() {


        /**
         * Este metodo crea un usuario, lo inserta en la base de datos y lo devuelve.
         */
        
        return null;
    }

    @Override
    public User getUser(UUID userId) {


        /**
         * Lee de la tabla los datos del usuario, en este caso solo el nombre, crea un instancia y la devuelve..
         */
        
        return null;
    }
}
