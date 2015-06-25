package com.bitdubai.fermat_api.layer.pip_actor.developer;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DatabaseTool {

    public void getAvailablePluginList (); //TODO ver que tipo de lista devuelve

    public void getAvailableAddonList (); //TODO ver que tipo de lista devuelve

    public void getDatabaseListFromPlugin (); //TODO ver que tipo de lista devuelve y que parametros enviarle

    public void getDatabaseListFromAddon (); //TODO ver que tipo de lista devuelve y que parametros enviarle

    public void getTableListFromDatabase (); //TODO ver que tipo de lista devuelve y que parametros enviarle

    public void getTableContent (); //TODO ver que tipo de lista devuelve y que parametros enviarle

}
