/*
 * @#AppMain.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai;

import com.bitdubai.entities.Fermat;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * The Class <code>com.bitdubai.AppMain</code> read and load
 * the content of the file FermatManifest.xml on java objects,
 * is the file content is valid no error show in console.
 *
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AppMain {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        try {

            File file = new File("FermatManifest.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Fermat.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Fermat fermat = (Fermat) jaxbUnmarshaller.unmarshal(file);

            System.out.println(fermat);
            System.out.println("FermatManifest.xml successfully read, all ok :)");

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
