package com.example.addressesSpringVersion.reader;

import com.example.addressesSpringVersion.entity.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class CSVReader {

    private final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public Collection<Location> read(String fileName) throws IOException {
        return read(new BufferedReader(new FileReader(fileName)));
    }

    public Collection<Location> read(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        Collection<Location> records = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(";");
            if(values.length > 1) {
                try {
                    records.add(new Location(csvValueToDouble(values[0]), csvValueToDouble(values[1])));
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return records;
    }

    private double csvValueToDouble(String value) throws NumberFormatException{
        return Double.parseDouble(value.replace(",", "."));
    }
}
