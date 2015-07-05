package com.bitdubai.sub_app.developer.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2015.07.04..
 */
public class ArrayListLoggers extends ArrayList<Loggers> implements List<Loggers>{

    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;



    public ArrayListLoggers(){

    }

    public ArrayListLoggers(int capacity) {
        super(capacity);
    }

    public boolean containsLevel0(Loggers log){
        boolean result=false;
        int i=0;
            while(!result && i<this.size()){
                Loggers logger=this.get(i);

                if(logger.level0.compareTo(log.level0)==0){
                    result= true;
                }
                i++;
            }
        return result;
    }

    public boolean containsLevel1(Loggers log){
        boolean result=false;
        int i=0;
        while(!result && i<this.size()){
            Loggers logger=this.get(i);

            if(logger.level1.compareTo(log.level1)==0){
                result= true;
            }
            i++;
        }
        return result;
    }

    public boolean containsLevel2(Loggers log){
        boolean result=false;
        int i=0;
        while(!result && i<this.size()){
            Loggers logger=this.get(i);

            if(logger.level2.compareTo(log.level2)==0){
                result= true;
            }
            i++;
        }
        return result;
    }

    public ArrayListLoggers getListFromLevel(Loggers logger,int level){
        ArrayListLoggers lstLoggers= new ArrayListLoggers();

        switch (level){
            case LEVEL_0:{

                for (Loggers log:this){
                    if(log.level0.compareTo(logger.level0)==0){
                        lstLoggers.add(log);
                    }
                }

                break;
            }
            case LEVEL_1:{
                for (Loggers log:this){
                    if(log.level1.compareTo(logger.level1)==0){
                        lstLoggers.add(log);
                    }
                }
                break;
            }
            case LEVEL_2:{
                for (Loggers log:this){
                    if(log.level2.compareTo(logger.level2)==0){
                        lstLoggers.add(log);
                    }
                }
                break;
            }
            case LEVEL_3:{
                for (Loggers log:this){
                    if(log.level3.compareTo(logger.level3)==0){
                        lstLoggers.add(log);
                    }
                }
                break;
            }
        }

        return lstLoggers;
    }

}
