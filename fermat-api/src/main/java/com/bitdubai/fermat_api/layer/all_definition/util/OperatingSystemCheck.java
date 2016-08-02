package com.bitdubai.fermat_api.layer.all_definition.util;

import java.util.Locale;

/**
 * Created by Roberto Requena on 21/04/16.
 */
public class OperatingSystemCheck {

    /**
     * Represent the Types of Operating Systems
     */
    public enum OperatingSystemType {
        Android, Windows, MacOS, Linux, Unix, Solaris, Other
    };

    /**
     * Represent the current operating system
     */
    protected static OperatingSystemType current;

    /**
     * Detect the operating system from the os.name System property and cache
     * the result into the current variable
     *
     * @returns OperatingSystemType - the operating system detected
     */
    public static OperatingSystemType getOperatingSystemType() {

        if (current == null) {

            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                current = OperatingSystemType.MacOS;
            } else if (OS.contains("win")) {
                current = OperatingSystemType.Windows;
            } else if (OS.contains("inux")) {

                String jvm = System.getProperty("java.vm.name", "generic").toLowerCase(Locale.ENGLISH);
                String jvmVendorUrl = System.getProperty("java.vendor.url", "generic").toLowerCase(Locale.ENGLISH);

                System.out.println("OperatingSystemCheck - jvm= "+ jvm);
                System.out.println("OperatingSystemCheck - jvmVendor= "+ jvmVendorUrl);

                if(jvmVendorUrl.equalsIgnoreCase("http://www.android.com/") || jvm.equalsIgnoreCase("Dalvik")){
                    current = OperatingSystemType.Android;
                }else {
                    current = OperatingSystemType.Linux;
                }

            } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")){
                current = OperatingSystemType.Unix;
            }else if (OS.contains("sunos")){
                current = OperatingSystemType.Solaris;
            }
            else {
                current = OperatingSystemType.Other;
            }

            System.out.println("OperatingSystemCheck - current = "+ current);

        }

        return current;
    }
    
}
