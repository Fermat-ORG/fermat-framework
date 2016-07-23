package com.bitdubai.fermat_cbp_api.all_definition.util;

import java.io.Serializable;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * Version class
 *
 * @author rodrigo
 * @version 1.0
 *          Class used to define an object version in the platform. Can be used to calculate comparissions between different versions.
 */
public class Version implements Serializable {
    Integer major;
    Integer minor;
    Integer patch;
    final String versionSeparator = ".";

    /**
     * Constructor
     *
     * @param major the first digit of the version. e.g 1.2.3. Major is number 1
     * @param minor the middle part of the version. e.g 1.2.3. Minor is number 2
     * @param patch the final part of th3e version. e.g 1.2.3. Patch is number 3
     */
    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Constructor
     *
     * @param version accepts the version in a string format, like 1.2.3
     */
    public Version(String version) {
        /**
         * Validates wrong versions
         */
        if (version == null)
            throw new IllegalArgumentException("Version can't be null");

        if (!version.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format");

        String[] numbers = version.split("\\.");

        if (!Integer.valueOf(numbers.length).equals(3))
            throw new IllegalArgumentException("Invalid version format");

        this.major = Integer.valueOf(numbers[0]);
        this.minor = Integer.valueOf(numbers[1]);
        this.patch = Integer.valueOf(numbers[2]);
    }


    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    /**
     * readable format of the version in the style of 1.0.0
     *
     * @return String.
     */
    @Override
    public String toString() {
        StringBuilder versionBuilder = new StringBuilder();
        versionBuilder.append(major);
        versionBuilder.append(versionSeparator);
        versionBuilder.append(minor);
        versionBuilder.append(versionSeparator);
        versionBuilder.append(patch);
        return versionBuilder.toString();
    }

    /**
     * Compares equality of two versions.
     *
     * @param obj
     * @return true if equal versions.
     */
    @Override
    public boolean equals(Object obj) {
        /**
         * calculates equality
         */
        if (!(obj instanceof Version))
            return false;

        Version version = (Version) obj;
        return this.major.equals(version.getMajor()) &&
                this.minor.equals(version.getMinor()) &&
                this.patch.equals(version.getPatch());
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) (Math.pow(this.getMajor(), 1) + Math.pow(this.getMinor(), 2) + Math.pow(this.getPatch(), 4));
        return result;
    }

    /**
     * Calculates if the version is between two versions.
     *
     * @param version1 lower version to compare to.
     * @param version2 higher version to comapte to.
     * @return true if the version is between version1 and version2.
     */
    public boolean isBetween(Version version1, Version version2) {
        return this.isAbove(version1) && !this.isAbove(version2) || this.equals(version1) && this.equals(version2);
    }

    /**
     * calculates if the version is above or not the passed version.
     *
     * @param version
     * @return true if above, false if below.
     */
    public boolean isAbove(Version version) {
        if (this.equals(version))
            return false;

        if (this.major > version.getMajor())
            return true;

        if (this.major == version.getMajor()) {
            if (this.minor > version.getMinor())
                return true;
            if (this.minor == version.getMinor()) {
                return this.patch > version.getPatch();
            } else
                return false;
        } else
            return false;
    }

}
