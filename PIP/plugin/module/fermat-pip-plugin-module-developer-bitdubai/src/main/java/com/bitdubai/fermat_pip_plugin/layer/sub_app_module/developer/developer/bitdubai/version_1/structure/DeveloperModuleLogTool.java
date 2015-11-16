package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetClasessHierarchyAddonsException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetClasessHierarchyPluginsException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.LogTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperModuleLogTool implements LogTool {


    private Map<PluginVersionReference,Plugin> LoggingLstPlugins;
    private Map<AddonVersionReference,Addon> LoggingLstAddons;

    public DeveloperModuleLogTool(Map<PluginVersionReference, Plugin> LoggingLstPlugins, Map<AddonVersionReference, Addon> LoggingLstAddons) {
        this.LoggingLstPlugins = LoggingLstPlugins;
        this.LoggingLstAddons = LoggingLstAddons;
    }

    @Override
    public List<PluginVersionReference> getAvailablePluginList() {
        List<PluginVersionReference> lstPlugins=new ArrayList<>();
        for(Map.Entry<PluginVersionReference, Plugin> entry : LoggingLstPlugins.entrySet()) {
            PluginVersionReference key = entry.getKey();
            lstPlugins.add(key);
        }
        return lstPlugins;
    }

    @Override
    public List<AddonVersionReference> getAvailableAddonList() {
        List<AddonVersionReference> lstAddons=new ArrayList<>();
        for(Map.Entry<AddonVersionReference, Addon> entry : LoggingLstAddons.entrySet()) {
            AddonVersionReference key = entry.getKey();
            lstAddons.add(key);
        }
        return lstAddons;
    }


    /**
     * I get from the plugin the list of classes with their full paths.
     * I transform that into a class to be passed to the android App.
     * @param plugin
     * @return
     */
    @Override
    public List<ClassHierarchyLevels> getClassesHierarchyPlugins(PluginVersionReference plugin) throws CantGetClasessHierarchyPluginsException{

        try
        {
            /**
             * I get the class full patch from the plug in.
             */
            List<String> classes = ((LogManagerForDevelopers)this.LoggingLstPlugins.get(plugin)).getClassesFullPath();
            //List<Class<?>> javaClasses = ClassFinder.find(((LogManagerForDevelopers) this.LoggingLstPlugins.get(plugin)).getClass().getPackage().getName());


            /**
             * I need to know the minimun number of packages on the plug in.
             * If there are more than three, then I will create only three levels         *
             */
            int minPackages=100, cantPackages = 0;
            for (String myClass : classes){
                String[] packages = myClass.split(Pattern.quote("."));
                cantPackages = packages.length;
                if (minPackages > cantPackages)
                    minPackages = cantPackages;
            }
            /**
             * minPackages holds the minimun number of packages available on the plug in.
             */

            /**
             * I instantiate the class that will hold the levels of the packages.
             * Level 1: root (which may contain a lot of packages)
             * Level 2: the last package
             * Level 3: the class name.
             */
            List<ClassHierarchyLevels> returnedClasses = new ArrayList<ClassHierarchyLevels>();


            for (String myClass : classes) {
                String[] packages = myClass.split(Pattern.quote("."));
                ClassHierarchyLevels classesAndPackages = new ClassHierarchyLevels();
                classesAndPackages.setLevel0(plugin.toKey());
                classesAndPackages.setFullPath(myClass);
                if (packages.length == minPackages) {
                    /**
                     * if this is the root, then I will add it to level2 and nothing else.
                     */
                    classesAndPackages.setLevel1(packages[packages.length - 1]);

                } else {
                    /**
                     * si no es el root, I will add the other levels.
                     */
                    StringBuilder splitedPackages = new StringBuilder();
                    for (int i = 0; i < packages.length - 1; i++) {
                        splitedPackages.append(packages[i]);
                        splitedPackages.append(".");
                    }
                    /**
                     * I remove the last dot of the package.
                     */
                    splitedPackages.substring(0, splitedPackages.length() - 1);

                    classesAndPackages.setLevel1(splitedPackages.toString());
                    classesAndPackages.setLevel2(packages[packages.length-1]);
                }
                returnedClasses.add(classesAndPackages);


            }
            /**
             * I return the object
             */
            return returnedClasses;
        }
        catch(Exception e)
        {
            throw new CantGetClasessHierarchyPluginsException(CantGetClasessHierarchyPluginsException.DEFAULT_MESSAGE,e,"Error to get from the plugin the list of classes with their full paths","");

        }

    }


    /*
        Created by matias, used in fragment to get loglevels
     */
    @Override
    public List<ClassHierarchyLevels> getClassesHierarchyAddons(AddonVersionReference addon) throws CantGetClasessHierarchyAddonsException {
        try
        {
            /**
             * I get the class full patch from the plug in.
             */
            List<String> classes = ((LogManagerForDevelopers)this.LoggingLstAddons.get(addon)).getClassesFullPath();
            //List<Class<?>> javaClasses = ClassFinder.find(((LogManagerForDevelopers) this.LoggingLstPlugins.get(plugin)).getClass().getPackage().getName());


            /**
             * I need to know the minimun number of packages on the plug in.
             * If there are more than three, then I will create only three levels         *
             */
            int minPackages=100, cantPackages = 0;
            for (String myClass : classes){
                String[] packages = myClass.split(Pattern.quote("."));
                cantPackages = packages.length;
                if (minPackages > cantPackages)
                    minPackages = cantPackages;
            }
            /**
             * minPackages holds the minimun number of packages available on the plug in.
             */

            /**
             * I instantiate the class that will hold the levels of the packages.
             * Level 1: root (which may contain a lot of packages)
             * Level 2: the last package
             * Level 3: the class name.
             */
            List<ClassHierarchyLevels> returnedClasses = new ArrayList<>();


            for (String myClass : classes) {
                String[] packages = myClass.split(Pattern.quote("."));
                ClassHierarchyLevels classesAndPackages = new ClassHierarchyLevels();
                classesAndPackages.setLevel0(addon.toKey());
                classesAndPackages.setFullPath(myClass);
                if (packages.length == minPackages) {
                    /**
                     * if this is the root, then I will add it to level2 and nothing else.
                     */
                    classesAndPackages.setLevel1(packages[packages.length - 1]);

                } else {
                    /**
                     * si no es el root, I will add the other levels.
                     */
                    StringBuilder splitedPackages = new StringBuilder();
                    for (int i = 0; i < packages.length - 1; i++) {
                        splitedPackages.append(packages[i]);
                        splitedPackages.append(".");
                    }
                    /**
                     * I remove the last dot of the package.
                     */
                    splitedPackages.substring(0, splitedPackages.length() - 1);

                    classesAndPackages.setLevel1(splitedPackages.toString());
                    classesAndPackages.setLevel2(packages[packages.length-1]);
                }
                returnedClasses.add(classesAndPackages);


            }
            /**
             * I return the object
             */
            return returnedClasses;
        }
        catch(Exception e)
        {
            throw new CantGetClasessHierarchyAddonsException(CantGetClasessHierarchyAddonsException.DEFAULT_MESSAGE,e,"Error to get from the plugin the list of classes with their full paths","");

        }
    }

    /**
     * This sets the new level in the plug in.
     * @param plugin
     * @param newLogLevelInClass
     */
    @Override
    public void setNewLogLevelInClass(Plugins plugin, HashMap<String, LogLevel> newLogLevelInClass) {
        ((LogManagerForDevelopers) this.LoggingLstPlugins.get(plugin)).setLoggingLevelPerClass(newLogLevelInClass);
    }

}
