package com.bitdubai.fermat_api.layer.all_definition.util;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by rodrigo on 7/29/15.
 * Used to define the versions in which this object will be valid.
 * Can be used to compare against the platform version or a Wallet version.
 */
public class VersionCompatibility implements Serializable {
    Version initialVersion;
    Version finalVersion;

    // platform version will be calculated with another plugin soon.
    final Version PLATFORM_VERSION = new Version("1.0.0");

    /**
     * Constructor
     *
     * @param initialVersion the initial Version in which this object will be executed. For example 1.0.0
     * @param finalVersion   the final Version in which this object will be executed. For example 2.0.0
     * @throws InvalidParameterException if the inital version is greater than the final, or viceversa
     */
    public VersionCompatibility(Version initialVersion, Version finalVersion) throws InvalidParameterException {
        this.initialVersion = initialVersion;
        this.finalVersion = finalVersion;

        if (initialVersion.isAbove(finalVersion))
            throw new InvalidParameterException("The final version must be greater or equal than the initial version", null, new StringBuilder().append("Initial Version: ").append(initialVersion.toString()).append(", final version: ").append(finalVersion.toString()).toString(), null);
    }

    public Version getInitialVersion() {
        return initialVersion;
    }

    public void setInitialVersion(Version initialVersion) throws InvalidParameterException {
        if (!finalVersion.isAbove(initialVersion))
            throw new InvalidParameterException("The initial version must be lower or equal than the final version", null, new StringBuilder().append("Initial Version: ").append(initialVersion.toString()).append(", final version: ").append(finalVersion.toString()).toString(), null);
        else
            this.initialVersion = initialVersion;
    }

    public Version getFinalVersion() {
        return finalVersion;
    }

    public void setFinalVersion(Version finalVersion) throws InvalidParameterException {
        if (this.initialVersion.isAbove(finalVersion))
            throw new InvalidParameterException("The final version must be greater or equal than the initial version", null, new StringBuilder().append("Initial Version: ").append(initialVersion.toString()).append(", final version: ").append(finalVersion.toString()).toString(), null);
        else
            this.finalVersion = finalVersion;
    }

    /**
     * Returns true if this VersionCompatibility is valid to run on this platform.
     *
     * @return
     */
    public boolean isValidInPlatform() {
        return PLATFORM_VERSION.isBetween(this.initialVersion, this.finalVersion);
    }

    /**
     * Returns true if the passed version is valid to be executed on this version object.
     *
     * @param version
     * @return
     */
    public boolean isValidInVersion(Version version) {
        return version.isBetween(this.initialVersion, this.finalVersion);
    }

    @Override
    public String toString() {
        return finalVersion.toString();
    }
}
