package ch.so.agi.avgbs.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private static String FOLDER_PREFIX = "avgbs_checkservice_";

    @Value("${app.pathToUploadFolder}")
    private String pathToUploadFolder;

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    CamelContext camelContext;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "upload";
    }
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam(name = "file", required = true) MultipartFile uploadFile, 
            Authentication authentication) {
     
        try {            
            // Get the file name.
            String fileName = uploadFile.getOriginalFilename();
            
            // If the upload button was pushed w/o choosing a file,
            // we just redirect to the starting page.
            if (uploadFile.getSize() == 0 || fileName.trim().equalsIgnoreCase("") || fileName == null) {
                log.warn("No file was uploaded. Redirecting to starting page.");
    
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", servletContext.getContextPath() + "/upload");
                return new ResponseEntity<String>(headers, HttpStatus.FOUND);
            }
    
            // Build the file path.
            Path tmpDirectory = Files.createTempDirectory(Paths.get(pathToUploadFolder), FOLDER_PREFIX);
            Path uploadFilePath = Paths.get(tmpDirectory.toString(), fileName);
    
            // Save the file.
            byte[] bytes = uploadFile.getBytes();
            Files.write(uploadFilePath, bytes);
            
            /*
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                log.info("****");
                log.info(currentUserName);
            }
            */
            
            // Send message to route with authentication information.
            ProducerTemplate template = camelContext.createProducerTemplate();
            
            Exchange exchange = ExchangeBuilder.anExchange(camelContext)
                    .withBody(uploadFilePath.toFile())
                    .withHeader(Exchange.AUTHENTICATION, authentication)
                    .withHeader(Exchange.FILE_NAME, uploadFilePath.toFile().getName()) // TODO: use file name only?
                    .build();

            // Asynchronous request
            //template.asyncSend("direct:avgbsCheckservice", exchange);
            
            // Synchronous request
            Exchange result = template.send("direct:avgbsCheckservice", exchange);
            
            if (result.isFailed()) {
                return ResponseEntity.badRequest().contentType(MediaType.parseMediaType("text/plain")).body(result.getException().getMessage());
            } else {
                return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("alles gut");

            }
            
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.badRequest().contentType(MediaType.parseMediaType("text/plain")).body(e.getMessage());
        }
    }


}
