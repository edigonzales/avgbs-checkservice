package ch.so.agi.avgbs.camel.processors.zipcontent;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.so.agi.avgbs.camel.processors.utils.ExtractZipFileVisitor;

@Component
public class ZipContentMatchesZipNameProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Exchange exchange) throws Exception {        
        String fileName = (String) exchange.getIn().getHeaders().get("CamelFileName");
        File dataFile = exchange.getIn().getBody(File.class);
        String fileNameNoExt = fileName.substring(0,fileName.length()-4);

        String dest = new File(dataFile.getAbsolutePath()).getParent();
        ArrayList<String> dataFileNames = new ArrayList<String>();

        Map<String, String> env = new HashMap<>();
        env.put("create", "false");
        URI uri = URI.create("jar:file://" + dataFile.getAbsolutePath());

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Iterable<Path> roots = zipfs.getRootDirectories();
            Path root = roots.iterator().next();

            ExtractZipFileVisitor ezfv = new ExtractZipFileVisitor(Paths.get(dest));
            Files.walkFileTree(root, ezfv);
            dataFileNames = ezfv.getFileList();
        }

        boolean xmlFound = false;
        boolean pdfFound = false;
        
        for (String dataFileName : dataFileNames) {
            File aDataFile = new File(dataFileName);

            if (dataFileName.toLowerCase().endsWith("xml")) {
                if (aDataFile.getName().substring(0, aDataFile.getName().length() - 4).equals(fileNameNoExt)) {
                    xmlFound = true;
                }
            }
            
            if (dataFileName.toLowerCase().endsWith("pdf")) {
                if (aDataFile.getName().substring(0, aDataFile.getName().length() - 4).equals(fileNameNoExt)) {
                    pdfFound = true;
                }
            }
        }

        if (!xmlFound || !pdfFound) {
            throw new ZipContentMatchesZipNameProcessorException("Zip file ("+fileName+") does not contain corresponding xml and/or pdf file.");
        }
    }
}
