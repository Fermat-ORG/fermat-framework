package com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_api.layer._5_user.UserTypes;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.CryptoAddressBook;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class AddressBook implements CryptoAddressBook , DealsWithPluginDatabaseSystem {


    /**
     * UsesDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

// TODO NATALIA: Seguiendo el patron de diseño de tener una clase con las constantes de base de datos y otra databasefactory, mas un metodo
    // Initialize en la interfaz de la clase, procede a crear la base de datos para este modulo, respetando los detalles de la nomenclatura.
    // Te detallo a continuacion las tablas y campos.
    
    // Tabla: AddressBook
    
    // Campos:
    
    // Id
    // IdUser
    // UserType (sale del enum UserTypes que debe seguir el patron de diseño de exponer un getCode() de 3 digitos)
    // CryptoAddress  (un string de la longitud exacta de la direcciones bitcoin, averigua cuanto es)
    
    // CRYPTO_ADDRESSES_FIRST_KEY_COLUM = "CryptoAddress"  ---> Tenes que implementar en el modulo de base de datos que se pueda definir
    // indices para las tablas declarando una constante como la anterior.
    
    // El metodo para agregar un indice seria:     table.addIndex(CRYPTO_ADDRESSES_FIRST_KEY_COLUM)


    /**
     * CryptoAddressBook interface implementation.
     */
    @Override
    public User getUserByCryptoAddress(CryptoAddress cryptoAddress) {


        /**
         * La clase en el metodo initialize ya deja abierta una conexion a la base de datos (ese es otro patron de diseño)
         * 
         * A traves de esa conexion, pedis una tabla, le seteas como filtro la crypto address y obtenes el registro.
         * 
         * Como este metodo devuelve un User que este plugin no puede crear, se lo tiene que pedir a alguno de los siguientes
         * tres plugins: DeviceUser, IntraUser, CryptoUser.
         * 
         * Para poder acceder a esos plugins debe implementar las interfaces 
         * 
         * DealsWithDeviceUsers
         * DealsWithExtraUsers
         * DealsWithIntraUsers
         * 
         * Segui el patron de diseño que tenemos para implementar las interfaces recordando que deben estar listadas en orden 
         * alfabetico y lo mismo su implementacion.
         * 
         * Basandote en el codigo de UserType guardado en la base y ya transforamdo en el tipo del enum.hace un swithc y
         * 
         * pedi un User al manager del plugin que corresponda pasandole el id del usuario (si no esta ese metodo escribilo)
         * 
         * Ese User luego es el que devolves como resultado de esta fucnnion.
         * 
         * * * * * * * 
         */
        
        
        return null;
    }

    @Override
    public void registerUserCryptoAddress(User user, CryptoAddress cryptoAddress) {

        /**
         * En esta caso pedis la tabla al a base de datos y simplemente vas a agregar un nuevo registro.
         * 
         * En el User recibido podes obtener tanto el Id como el UserType con lo cual tenes toda la informacion para 
         * agregar en la base de datos.
         *
         * * * * * *
         */
        
    }



    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

}
