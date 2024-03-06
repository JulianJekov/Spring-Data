package org.example._09_xmlprocessingexercises.constants;

import org.modelmapper.ModelMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public enum Utils {
    ;

    public static final ModelMapper MAPPER = new ModelMapper();


    public static void writeIntoJsonFile(List<?> objects, Path fieldPath) throws IOException {
        FileWriter fileWriter = new FileWriter(fieldPath.toFile());

        fileWriter.flush();
        fileWriter.close();
    }

    public static void writeIntoJsonFile(Object object, Path fieldPath) throws IOException {
        FileWriter fileWriter = new FileWriter(fieldPath.toFile());

        fileWriter.flush();
        fileWriter.close();
    }


    public static <T> void writeIntoXmlFile(T wrapper, Path path) throws JAXBException {
        final File file = path.toFile();

        final JAXBContext context = JAXBContext.newInstance(wrapper.getClass());
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(wrapper, file);
    }

}