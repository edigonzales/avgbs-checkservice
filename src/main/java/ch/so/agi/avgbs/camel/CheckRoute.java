package ch.so.agi.avgbs.camel;


import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.so.agi.avgbs.camel.processors.authorisation.AuthorisationProcessor;
import ch.so.agi.avgbs.camel.processors.idendnd.EnabledIdentNDProcessor;
import ch.so.agi.avgbs.camel.processors.ilivalidator.IlivalidatorProcessor;
import ch.so.agi.avgbs.camel.processors.zipcontent.ZipContentMatchesZipNameProcessor;
import ch.so.agi.avgbs.camel.processors.zipname.ZipNameMatchesDataProcessor;

@Component
public class CheckRoute extends RouteBuilder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.pathToUploadFolder}")
    private String pathToUploadFolder;

    @Value("${app.pathToProcessFolder}")
    private String pathToProcessFolder;

    @Autowired
    EnabledIdentNDProcessor enabledIdentNDProcessor;

    @Autowired
    AuthorisationProcessor authorisationProcessor;
    
    @Autowired
    ZipContentMatchesZipNameProcessor zipContentMatchesZipNameProcessor;
    
    @Autowired
    IlivalidatorProcessor ilivalidatorProcessor;

    @Autowired
    ZipNameMatchesDataProcessor zipNameMatchesDataProcessor;

    @Override
    public void configure() throws Exception {

        from("direct:avgbsCheckservice")
        .process(enabledIdentNDProcessor)
        .log(LoggingLevel.INFO, "EnabledIdentND successfully passed.")
        .process(authorisationProcessor)
        .log(LoggingLevel.INFO, "Authorisation successfully passed.")
        .process(zipContentMatchesZipNameProcessor)
        .log(LoggingLevel.INFO, "ZipContentMatchesZipName successfully passed.")
        .process(zipNameMatchesDataProcessor)
        .log(LoggingLevel.INFO, "ZipNameMatchesData successfully passed.")        
        .process(ilivalidatorProcessor)
        .log(LoggingLevel.INFO, "Ilivalidator successfully passed.")
        .to("file:///Users/stefan/tmp/");
    }
}
