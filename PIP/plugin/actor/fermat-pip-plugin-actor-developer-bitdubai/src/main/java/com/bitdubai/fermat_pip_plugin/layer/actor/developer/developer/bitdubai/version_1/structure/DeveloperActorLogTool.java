package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorLogTool implements LogTool {


    private Map<Plugins,Plugin> LoggingLstPlugins;
    private Map<Addons,Addon> LoggingLstAddons;

    public DeveloperActorLogTool(Map<Plugins, Plugin> LoggingLstPlugins, Map<Addons, Addon> LoggingLstAddons) {
        this.LoggingLstPlugins = LoggingLstPlugins;
        this.LoggingLstAddons = LoggingLstAddons;
    }

    @Override
    public List<Plugins> getAvailablePluginList() {
        List<Plugins> lstPlugins=new ArrayList<Plugins>();
        for(Map.Entry<Plugins, Plugin> entry : LoggingLstPlugins.entrySet()) {
            Plugins key = entry.getKey();
            lstPlugins.add(key);
        }
        return lstPlugins;
    }

    @Override
    public List<Addons> getAvailableAddonList() {
        List<Addons> lstAddons=new ArrayList<Addons>();
        for(Map.Entry<Addons, Addon> entry : LoggingLstAddons.entrySet()) {
            Addons key = entry.getKey();
            lstAddons.add(key);
        }
        return lstAddons;
    }

    @Override
    public LogLevel getLogLevel(Plugins plugin) {
       return ((LogManagerForDevelopers)this.LoggingLstPlugins.get(plugin)).getLoggingLevel();
    }

    @Override
    public LogLevel getLogLevel(Addons addon) {
        return ((LogManagerForDevelopers)this.LoggingLstAddons.get(addon)).getLoggingLevel();
    }

    @Override
    public void setLogLevel(Plugins plugin, LogLevel newLogLevel) {
        ((LogManagerForDevelopers)this.LoggingLstPlugins.get(plugin)).changeLoggingLevel(newLogLevel);

    }

    @Override
    public void setLogLevel(Addons addon, LogLevel newLogLevel) {
        ((LogManagerForDevelopers)this.LoggingLstAddons.get(addon)).changeLoggingLevel(newLogLevel);
    }

    /**
     * I get from the plugin the list of classes with their full paths.
     * I transform that into a class to be passed to the android App.
     * @param plugin
     * @return
     */
    @Override
    public List<ClassHierarchyLevels> getClassesHierarchy(Plugins plugin) {
        /**
         * I get the class full patch from the plug in.
         */
        List<String> classes = ((LogManagerForDevelopers)this.LoggingLstPlugins.get(plugin)).getClassesFullPath();

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

        if (minPackages >=  4){
            for (String myClass : classes){
                String[] packages = myClass.split(Pattern.quote("."));
                StringBuilder splitedPackages = new StringBuilder();
                for (int i=0; i<packages.length-2;i++){
                    splitedPackages.append(packages[i]);
                    splitedPackages.append(".");
                }
                /**
                 * I remove the last dot of the package.
                 */
                splitedPackages.substring(0, splitedPackages.length() -1);

                /**
                 * I add the packages to each level.
                 */
                ClassHierarchyLevels classesAndPackages = new ClassHierarchyLevels();
                classesAndPackages.setLevel1(splitedPackages.toString());
                classesAndPackages.setLevel2(packages[packages.length - 2]);
                classesAndPackages.setLevel3(packages[packages.length - 1]);
                classesAndPackages.setFullPath(myClass);
                /**
                 * I add the full path for future reference.
                 */
                returnedClasses.add(classesAndPackages);
                splitedPackages.delete(0,splitedPackages.length()-1 );
            }
        } else
        /**
         * If there are less four I add the levels I have.
         */
        {
            for (String myClass : classes) {
                String[] packages = myClass.split(Pattern.quote("."));
                ClassHierarchyLevels classesAndPackages = new ClassHierarchyLevels();
                classesAndPackages.setLevel1(packages[0]);

                /**
                 * If I had one more level, I will add it
                 */
                if (packages.length > 1)
                    classesAndPackages.setLevel2(packages[1]);

                if (packages.length > 2)
                    classesAndPackages.setLevel3(packages[2]);

                classesAndPackages.setFullPath(myClass);
                /**
                 * I add the class to the returning object
                 */
                returnedClasses.add(classesAndPackages);
            }
        }

        /**
         * I return the object
         */
        return returnedClasses;
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
