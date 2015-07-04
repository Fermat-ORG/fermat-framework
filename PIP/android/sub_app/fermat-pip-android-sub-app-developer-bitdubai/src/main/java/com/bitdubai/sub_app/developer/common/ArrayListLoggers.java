package com.bitdubai.sub_app.developer.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2015.07.04..
 */
public class ArrayListLoggers extends ArrayList<Loggers> implements List<Loggers>{


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
        for(Loggers logger:this){
            if(logger.level1.compareTo(log.level1)==0){
                return true;
            }
        }
        return false;
    }

    public boolean containsLevel2(Loggers log){
        for(Loggers logger:this){
            if(logger.level2.compareTo(log.level2)==0){
                return true;
            }
        }
        return false;
    }

}
