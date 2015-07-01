package LoggerTests;

import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by rodrigo on 2015.07.01..
 */

public class GetClassTree {
    @Test
    public void getClassHierarchy(){
        /**
         * I get the class full patch from the plug in.
         */
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        List<String> classes = root.getClassesFullPath();

        /**
         * I need to know the minimun number of packages on the plug in.
         * If there are more than three, then I will create only three levels
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
        List<ClassHierarchy> returnedClasses = new ArrayList<ClassHierarchy>();

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
                ClassHierarchy classesAndPackages = new ClassHierarchy();
                classesAndPackages.setLevel1(splitedPackages.toString());
                classesAndPackages.setLevel2(packages[packages.length - 2]);
                classesAndPackages.setLevel3(packages[packages.length -1]);
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
                ClassHierarchy classesAndPackages = new ClassHierarchy();
                classesAndPackages.setLevel1(packages[0]);

                /**
                 * If I had one more level, I will add it
                 */
                if (packages.length > 1)
                classesAndPackages.setLevel2(packages[1]);

                if (packages.length > 2)
                    classesAndPackages.setLevel3(packages[2]);

                /**
                 * I add the class to the returning object
                 */
                returnedClasses.add(classesAndPackages);
                }
        }

        /**
         * I return the object
         */
        //return returnedClasses;


    }

    class ClassHierarchy{
        String level1;
        String level2;
        String level3;


        public String getLevel1() {
            return level1;
        }

        public String getLevel2() {
            return level2;
        }

        public String getLevel3() {
            return level3;
        }

        public void setLevel1(String level1) {
            this.level1 = level1;
        }

        public void setLevel2(String level2) {
            this.level2 = level2;
        }

        public void setLevel3(String level3) {
            this.level3 = level3;
        }
    }
}
