package com.bitdubai.fermat_api.module_object_creator;

import java.io.Serializable;

/**
 * Created by mati on 2016.04.18..
 */
public interface FermatModuleObjectInterface extends Serializable {
    /**
     * En este metodo se les entrega el mismo serializable que armaron en el writeToSerializable para que puedan reconstruir su objeto
     *
     * @param objectCreator
     */

    void creator(Serializable objectCreator);

    int describeContent();

    /**
     * En este metodo tienen que serializar agrupar el objeto en un Serializable que luego será pasado al creator para que puedan volver a armar dicho objeto
     *
     * @return
     */
    Serializable writeToSerializable();
}
