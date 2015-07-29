package com.bitdubai.fermat_api.layer.all_definition.util;

import com.thoughtworks.xstream.XStream;

/**
 * Created by Matias Furszyfer on 2015.07.30..
 */


public class XMLParser {

    public static String parseObject(Object object){
        String xml=null;
        if (object != null) {
            XStream xStream = getXstreamObject();

            xml = xStream.toXML(object);

            System.out.println("SerializedCountry XML:"+xml);

        }
        return xml;
    }

    public static Object parseXML(String xml,Object root){
        Object object=null;
        if (xml != null) {
                XStream xStream = getXstreamObject();
                object=xStream.fromXML(xml,root);
        }
        return object;
    }

    private static XStream getXstreamObject() {
        XStream xstream = new XStream(); // DomDriver and StaxDriver instances also can be used with constructor
        return xstream;
    }
}
