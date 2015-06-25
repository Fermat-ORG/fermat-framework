package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorDatabaseTool implements DatabaseTool {


    // La verdad algo no me queda claro, de donde saco los plugins, yo pensaba que esto se conectaba al root y el root te lo
    //devolvia de algún lado como interfaz pero es al reves, esto se tiene que conectar con algo del fermat-api
    // para que devuelva las cosas no?
    //
    ActorDeveloperPluginRoot root;

    DeveloperActorDatabaseTool(ActorDeveloperPluginRoot actorDeveloperPluginRoot){
        //this.actorDeveloperPluginRoot=actorDeveloperPluginRoot;
    }

    @Override
    public List<Plugins> getAvailablePluginList() {

        //por lo que me dijiste y entendí acá tengo que devolver la lista del hashMap
        //root.get
        return null;
    }

    @Override
    public List<Addons> getAvailableAddonList() {
        return null;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseListFromPlugin(Plugins plugin) {
        return null;
    }
    //voy a buscar en el hashMap la referencia
    //DatabaseManagersForDevelopers

    @Override
    public List<DeveloperDatabase> getDatabaseListFromAddon(Addons Addon) {

    }

    @Override
    public List<DeveloperDatabaseTable> getTableListFromDatabase(DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getTableContent(DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }
}
