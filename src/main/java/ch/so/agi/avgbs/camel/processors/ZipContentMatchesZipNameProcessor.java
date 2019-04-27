package ch.so.agi.avgbs.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ZipContentMatchesZipNameProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("ContentMatchesZipNameProcessor");
    }

}
