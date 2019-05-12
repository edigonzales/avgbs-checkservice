package ch.so.agi.avgbs.camel.processors;

import static org.hamcrest.CoreMatchers.endsWith;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZipContentMatchesZipNameProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        Long MILLS_IN_DAY = 86400000L;
        
        String fileName = (String) exchange.getIn().getHeaders().get("CamelFileName");
        String fileNameNoExt = fileName.substring(0,fileName.length()-4);
        
        File dataFile = exchange.getIn().getBody(File.class);

        boolean xmlFound = false;
        boolean pdfFound = false;

        // TODO: ignore subfolder ("__MACOSX")...
        // Try: https://bitbucket.org/edigonzales/freeframe-spring-boot/src/master/src/freeframe/src/main/java/org/catais/freeframe/service/FreeframeService.java
        // Also in ilivalidator processor 
        
        try (FileInputStream fis = new FileInputStream(dataFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ZipInputStream zis = new ZipInputStream(bis)) {
            
            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {
                if (ze.getName().toLowerCase().endsWith("xml")) {
                    if (ze.getName().substring(0,ze.getName().length()-4).equals(fileNameNoExt)) {
                        xmlFound = true;
                    }
                }
                
                if ( ze.getName().toLowerCase().endsWith("pdf")) {
                    if (ze.getName().substring(0,ze.getName().length()-4).equals(fileNameNoExt)) {
                        pdfFound = true;
                    }
                }
                
                log.info("File: " + ze.getName());
                log.info("Size: " + ze.getSize());
                log.info("Last Modified: " + LocalDate.ofEpochDay(ze.getTime() / MILLS_IN_DAY));
            }
        }
        
        if (!pdfFound || !xmlFound) {
            throw new ZipContentMatchesZipNameProcessorException("Zip file ("+fileName+") does not contain corresponding xml and/or pdf file.");
        }
    }
}
