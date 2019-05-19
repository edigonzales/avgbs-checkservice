package ch.so.agi.avgbs.camel.processors.ilivalidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.ehi.basics.settings.Settings;
import org.interlis2.validator.Validator;

@Component
public class IlivalidatorProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        String fileName = (String) exchange.getIn().getHeaders().get("CamelFileName");
        String fileNameNoExt = fileName.substring(0,fileName.length()-4);
        log.info(fileNameNoExt);

        File dataFile = exchange.getIn().getBody(File.class);
        File destDir = new File(dataFile.getParentFile().getAbsolutePath());
        
        // We assume the file we need for this validation are present.
        // This is assumption is validated before!
        
        String logFileName = Paths.get(destDir.getAbsolutePath(), fileNameNoExt + ".log").toFile().getAbsolutePath();
        
        Settings settings = new Settings();
        settings.setValue(Validator.SETTING_ILIDIRS, Validator.SETTING_DEFAULT_ILIDIRS);
        settings.setValue(Validator.SETTING_LOGFILE, logFileName);

        String xmlFile = Paths.get(destDir.getAbsolutePath(), fileNameNoExt + ".xml").toFile().getAbsolutePath();
        boolean valid = Validator.runValidation(xmlFile, settings);
        
        if (!valid) {
            String logFileContent = new String(Files.readAllBytes(Paths.get(logFileName)));
            throw new IlivalidatorProcessorException(logFileContent);
        }

    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
}
