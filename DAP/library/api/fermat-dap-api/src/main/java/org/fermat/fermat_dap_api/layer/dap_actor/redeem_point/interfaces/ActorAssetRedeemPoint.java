package org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetRedeemPoint extends DAPActor, Serializable {

    /**
     * The method <code>getRegistrationDate</code> gives us the date when both Redeem Points
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getRegistrationDate();

    /**
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented
     * Asset Issuer
     *
     * @return the Connection Date
     */
    long getLastConnectionDate();

    /**
     * The method <code>getConnectionState</code> gives us the connection state of the represented
     * Asset Issuer
     *
     * @return the Connection state
     */
    DAPConnectionState getDapConnectionState();

    /**
     * The method <code>getLocation</code> gives us the Location of the represented
     * Redeem Point
     *
     * @return the Location
     */
    Location getLocation();

    /**
     * The method <code>getLocationLatitude</code> gives us the Location of the represented
     * Redeem Point
     *
     * @return the Location Latitude
     */
    Double getLocationLatitude();

    /**
     * The method <code>getLocationLongitude</code> gives us the Location of the represented
     * Redeem Point
     *
     * @return the Location Longitude
     */
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

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    CryptoAddress getCryptoAddress();

    BlockchainNetworkType getBlockchainNetworkType();

    List<String> getRegisteredIssuers();
}
