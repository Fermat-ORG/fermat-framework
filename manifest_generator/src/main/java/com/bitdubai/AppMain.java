package com.bitdubai;

import com.bitdubai.entities.Fermat;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class AppMain {

    public static void main(String[] args) {

        try {

            File file = new File("FermatManifest.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Fermat.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Fermat fermat = (Fermat) jaxbUnmarshaller.unmarshal(file);

            System.out.println(fermat);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
