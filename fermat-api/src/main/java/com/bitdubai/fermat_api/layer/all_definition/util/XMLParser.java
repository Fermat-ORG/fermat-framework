package com.bitdubai.fermat_api.layer.all_definition.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;

/**
 * Created by Matias Furszyfer on 2015.07.30..
 */


/**
 * XML Parser convert objects to xml string to save in file and viceversa
 */

public class XMLParser {

    /**
     * the method receives an object and returns a parsing of set object in a xml format string,
     *
     * @param object
     * @return String xml
     */

    public static String parseObject(Object object) {
        String xml = null;
        if (object != null) {
            XStream xStream = getXstreamObject();

            xml = xStream.toXML(object);


        }
        return xml;
    }

    /**
     * the method receives a string in xml format and a root object to instantiate, returning the new objects loaded with the xml data
     *
     * @param xml
     * @param root
     * @return object
     */

    public static Object parseXML(String xml, Object root) {
        Object object = null;
        try {
            if (xml != null) {
                XStream xStream = getXstreamObject();
                object = xStream.fromXML(xml, root);
            }
        } catch (ConversionException e) {
            throw new LibraryException("Error in xml library", e);
        }
        return object;
    }

    /**
     * Obtain XStream object
     *
     * @return XStream
     */

    private static XStream getXstreamObject() {
        XStream xstream = new XStream(); // DomDriver and StaxDriver instances also can be used with constructor
        return xstream;
    }
}
