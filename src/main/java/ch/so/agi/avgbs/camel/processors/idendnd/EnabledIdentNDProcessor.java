package ch.so.agi.avgbs.camel.processors.idendnd;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.so.agi.avgbs.models.IdentND;
import ch.so.agi.avgbs.repositories.IdentNDRepository;

@Component
public class EnabledIdentNDProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private IdentNDRepository identNDRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        String fileName = (String) exchange.getIn().getHeaders().get("CamelFileName");
        String messageIdentND = fileName.substring(0,12); 
        
        IdentND identND = identNDRepository.findByIdentnd(messageIdentND).orElse(null);
        
        if (identND == null) {
            throw new IllegalArgumentException("IdentND not found: " + messageIdentND);
        }
        
        boolean enabled = identND.getEnabled();
        
        if (!enabled) {
            throw new EnabledIdentNDProcessorException(messageIdentND + " is not enabled.");
        }
    }
}
