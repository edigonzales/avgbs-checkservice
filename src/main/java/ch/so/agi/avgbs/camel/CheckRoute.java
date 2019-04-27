package ch.so.agi.avgbs.camel;

import java.nio.ByteBuffer;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.so.agi.avgbs.camel.processors.AuthorisationProcessor;
import ch.so.agi.avgbs.camel.processors.ZipContentMatchesZipNameProcessor;

@Component
public class CheckRoute extends RouteBuilder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.pathToUploadFolder}")
    private String pathToUploadFolder;

    @Value("${app.pathToProcessFolder}")
    private String pathToProcessFolder;

    @Override
    public void configure() throws Exception {
//        from("file://"+pathToUploadFolder+"/?noop=true&delay=5000&initialDelay=3000&readLock=changed&antInclude=**/*.zip&recursive=true&idempotentRepository=#fileConsumerRepo&idempotentKey=copy-process-${file:name}-${file:size}-${file:modified}")
//        .to("file://"+pathToProcessFolder);
        
        
        
        
        
        from("direct:avgbsCheckservice")
        .process(new AuthorisationProcessor())
        .log(LoggingLevel.INFO, "AuthorisationProcessor successfully passed.")
        .process(new ZipContentMatchesZipNameProcessor())
        .to("file:///Users/stefan/tmp/");
        
        
        //from("file://"+pathToProcessFolder+"/?noop=true&delay=5000&initialDelay=5000&readLock=changed&antInclude=**/*.zip&recursive=true&idempotentRepository=#fileConsumerRepo&idempotentKey=unzip-${file:name}-${file:size}-${file:modified}")
//        from("file://"+pathToProcessFolder+"/?noop=true&delay=5000&initialDelay=5000&readLock=changed&antInclude=**/*.zip&recursive=false")
//        .process(new ZipContentMatchesZipNameProcessor());
        //.process(processor);
//        .split(new ZipSplitter())
//        .streaming().convertBodyTo(ByteBuffer.class) 
//            .choice()
//                .when(body().isNotNull())
//                    .toD("file://${in.header.CamelFileParent}?flatten=false") 
//            .end()
//        .end();        

    }
}
