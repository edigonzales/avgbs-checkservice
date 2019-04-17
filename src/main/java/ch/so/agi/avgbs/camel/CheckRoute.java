package ch.so.agi.avgbs.camel;

import java.nio.ByteBuffer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CheckRoute extends RouteBuilder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.pathToUploadFolder}")
    private String pathToUploadFolder;

    @Value("${app.pathToProcessFolder}")
    private String pathToProcessFolder;

    @Override
    public void configure() throws Exception {
        from("file://"+pathToUploadFolder+"/?noop=true&delay=5000&initialDelay=3000&readLock=changed&antInclude=**/*.zip&recursive=true&idempotentRepository=#fileConsumerRepo&idempotentKey=copy-process-${file:name}-${file:size}-${file:modified}")
        .to("file://"+pathToProcessFolder);
        
        from("file://"+pathToProcessFolder+"/?noop=true&delay=5000&initialDelay=5000&readLock=changed&antInclude=**/*.zip&recursive=true&idempotentRepository=#fileConsumerRepo&idempotentKey=unzip-${file:name}-${file:size}-${file:modified}")
        .split(new ZipSplitter())
        .streaming().convertBodyTo(ByteBuffer.class) 
            .choice()
                .when(body().isNotNull())
                    .toD("file://${in.header.CamelFileParent}?flatten=false") 
            .end()
        .end();        

    }
}
