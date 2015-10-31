package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetRedeemPoint {

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Redeem Point
     *
     * @return the public key
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented Redeem Point
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Redeem Points
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getRegistrationDate();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Redeem Point
     *
     * @return the image
     */
    byte[] getProfileImage();


    /**
     * Método {@code getCryptoAddress()}:
     *
     * @return {@link CryptoAddress} con la información criptográfica del Redeem Point.
     */
    CryptoAddress getCryptoAddress();


    /**
     * Método {@code getConnectionState()}
     * Representa el estado de conexión en el que se encuentra este Redeem Point actualmente.
     *
     * @return instancia de {@link ConnectionState} asociada al Redeem Point.
     */
    ConnectionState getConnectionState();


    /**
     * Metodo {@code getLocation()}:
     * devuelve la ubicacion geografica del RedeemPoint.
     *
     * @return un objeto {@link com.bitdubai.fermat_api.layer.osa_android.location_system.Location}
     * que contiene las coordenadas y demas valores para ubicar al RedeemPoint en el mapa.
     */
    Location getLocation();

    Double getLocationLatitude();

    Double getLocationLongitude();
    /**
     * Metodo {@code getAddress()}:
     * devuelve la direccion en el que esta ubicado un redeem point, a diferencia del metodo
     * {@link ActorAssetRedeemPoint#getLocationLatitude()#getLocationLongitude}
     * la informacion devuelta por este metodo,
     * aunque ambas puedan apuntar al mismo lugar, se presenta de una manera mas amigable
     * para el usuario que coordenadas geograficas.
     *
     * @return un objeto {@link Address} con la informacion pertinente a la ubicacion del
     * Redeem Point.
     */
    Address getAddress();

    /**
     * Metodo {@code getContactInformation()}:
     * devuelve la informacion de contacto que puede poseer un Redeem Point,
     * debido a que pueden ser muchas se decidio almacenarse como String para
     * simplemente imprimirsele al usuario.
     * Esta podria no ser la mejor implementacion ya que esto limitara el acceso
     * del usuario para con la informacion de contacto, ejemplo: no podra tocar
     * un numero de telefono y llamarlo sino que debera copiarlo.
     *
     * @return {@link String} con los datos necesarios para que el actor
     * se comunique con el Redeem Point por medios externos a Fermat.
     */
    String getContactInformation();

    /**
     * Metodo {@code getHoursOfOperation()}:
     * devuelve el horario laboral del Redeem Point, para que el Actor pueda
     * dirigirse a él a utilizar sus Asset, debido a que no hay un estándares
     * de horarios de trabajo y se pueden tener distintos turnos u horarios de
     * apertura esto se representa como un alfanumérico y se deja a libertad
     * del creator su escritura.
     *
     * @return {@link String} con la informacion de los horarios laborales del
     * Redeem Point.
     */
    String getHoursOfOperation();

}
