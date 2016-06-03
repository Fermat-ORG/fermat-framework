package com.bitdubai.sub_app.developer.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.07.04..
 */
public class ArrayListLoggers extends ArrayList<Loggers> implements List<Loggers> {

    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;

    public ArrayListLoggers() {

    }

    public boolean containsLevel0(Loggers log) {
        boolean result = false;
        int i = 0;
        while (!result && i < this.size()) {
            Loggers logger = this.get(i);

            if (logger.classHierarchyLevels.getLevel0().compareTo(log.classHierarchyLevels.getLevel0()) == 0) {
                result = true;
            }
            i++;
        }
        return result;
    }

    public boolean containsLevel1(Loggers log) {
        boolean result = false;
        int i = 0;
        while (!result && i < this.size()) {
            Loggers logger = this.get(i);

            if (logger.classHierarchyLevels.getLevel1().compareTo(log.classHierarchyLevels.getLevel1()) == 0) {
                result = true;
            }
            i++;
        }
        return result;
    }

    public boolean containsLevel2(Loggers log) {
        boolean result = false;
        int i = 0;
        while (!result && i < this.size()) {
            Loggers logger = this.get(i);

            if (logger.classHierarchyLevels.getLevel2().compareTo(log.classHierarchyLevels.getLevel2()) == 0) {
                result = true;
            }
            i++;
        }
        return result;
    }

    public boolean containsLevel3(Loggers log) {
        boolean result = false;
        int i = 0;
        while (!result && i < this.size()) {
            Loggers logger = this.get(i);

            if (logger.classHierarchyLevels.getLevel3().compareTo(log.classHierarchyLevels.getLevel3()) == 0) {
                result = true;
            }
            i++;
        }
        return result;
    }

    public ArrayListLoggers getListFromLevel(Loggers logger, int level) {
        ArrayListLoggers lstLoggers = new ArrayListLoggers();

        switch (level) {
            case LEVEL_0: {

                for (Loggers log : this) {
                    if (log.classHierarchyLevels.getLevel0().compareTo(logger.classHierarchyLevels.getLevel0()) == 0) {
                        lstLoggers.add(log);
                    }
                }

                break;
            }
            case LEVEL_1: {
                for (Loggers log : this) {
                    if (log.classHierarchyLevels.getLevel1().compareTo(logger.classHierarchyLevels.getLevel1()) == 0) {
                        lstLoggers.add(log);
                    }
                }
                break;
            }
            case LEVEL_2: {
                for (Loggers log : this) {
                    if (log.classHierarchyLevels.getLevel2() != null) {
                        if (log.classHierarchyLevels.getLevel2().compareTo(logger.classHierarchyLevels.getLevel2()) == 0) {
                            lstLoggers.add(log);
                        }
                    }
                }
                break;
            }
            case LEVEL_3: {
                for (Loggers log : this) {
                    if (log.classHierarchyLevels.getLevel3() != null) {
                        if (log.classHierarchyLevels.getLevel2().compareTo(logger.classHierarchyLevels.getLevel2()) == 0) {
                            lstLoggers.add(log);
                        }
                    }
                }
                break;
            }
        }

        return lstLoggers;
    }

}
