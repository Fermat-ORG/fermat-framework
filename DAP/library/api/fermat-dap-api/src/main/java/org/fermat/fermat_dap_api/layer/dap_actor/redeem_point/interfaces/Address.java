package org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

/**
 * Created by Víctor A. Mars M. on 16/10/15.
 */
public interface Address {


    /**
     * Metodo getCountryName:
     * La direccion debe hacer referencia al pais donde esta ubicada,
     * dicho nombre del pais puede obtenerse a traves de una api externa
     * que proporcione la lista de los mismos como lo es: http://www.geonames.org/
     * O la API interna de Java {@link java.util.Locale}.
     *
     * @return {@link String} con el nombre completo del pais.
     */
    String getCountryName();

    /**
     * Metodo getProvinceName:
     * La direccion debe hacer referencia a la provincia donde esta ubicada,
     * provincia es un nombre generico elegido que dependiendo la division
     * politica y/o administrativa de un pais puede representar: estados,
     * departamentos, provincias, regiones, etc.
     *
     * @return {@link String} con el nombre completo de la provincia.
     */
    String getProvinceName();


    /**
     * Metodo getCityName:
     * La ciudad donde está ubicada la dirección. Puede entenderse por
     * CityName el nombre de un pueblo o barrio, algo que esté dentro
     * de una {@link Address#getProvinceName()} y sirva para hacer
     * referencia dentro de ella.
     *
     * @return {@link String} con el nombre completo de la ciudad.
     */
    String getCityName();

    /**
     * Metodo getStreetName:
     * Street hace referencia a una calle, pero tambien puede representar
     * una avenida, un boulevard, etc. por dichos casos es sugerido que antes
     * del numero o nombre se agregue un prefijo representando lo que es
     * por ejemplo {@code "Av. 15A, St. 53"}
     *
     * @return {@link String} con el nombre completo de la calle.
     */
    String getStreetName();

    /**
     * Metodo getPostalCode:
     * Aunque el codigo postal es comunmente representado por numeros,
     * segun el estandar internacional aceptado tambien pueden aceptar
     * letras, espacios o guiones por lo que es representado como un alfanumerico.
     *
     * @return {@link String} con el nombre codigo postal de la direccion.
     */
    String getPostalCode();


    /**
     * Metodo getHouseNumber:
     * HouseNumber hace referencia al numero (que puede contener otros caracteres
     * alfanumericos) en el que se esta ubicado, a pesar de que su nombre sugiera
     * que es para una casa esto tambien hace referencia a numero de local, de quinta,
     * y en caso de que sea un apartamento o este ubicado en un edificio debe hacerse referencia
     * al piso en el que se encuentra.
     *
     * @return {@link String} con el numero o nombre de la casa o local.
     */
    String getHouseNumber();

}
