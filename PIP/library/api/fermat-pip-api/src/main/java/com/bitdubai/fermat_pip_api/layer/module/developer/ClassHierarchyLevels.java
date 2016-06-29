package com.bitdubai.fermat_pip_api.layer.module.developer;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

import java.io.Serializable;

/**
 * Created by rodrigo on 2015.07.01..
 */
public class ClassHierarchyLevels implements Serializable {
    String level0;
    String level1;
    String level2;
    String level3;
    String fullPath;
    LogLevel logLevel;


    public String getLevel0() {
        return level0;
    }

    public void setLevel0(String level0) {
        this.level0 = level0;
    }

    public String getLevel1() {
        return level1;
    }

    public String getLevel2() {
        return level2;
    }

    public String getLevel3() {
        return level3;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
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

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
