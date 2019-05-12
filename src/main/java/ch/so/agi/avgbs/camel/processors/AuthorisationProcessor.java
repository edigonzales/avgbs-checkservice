package ch.so.agi.avgbs.camel.processors;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import ch.so.agi.avgbs.models.IdentND;
import ch.so.agi.avgbs.models.Role;
import ch.so.agi.avgbs.repositories.IdentNDRepository;

@Component
public class AuthorisationProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private IdentNDRepository identNDRepository;
        
    @Override
    public void process(Exchange exchange) throws Exception {
        // Get identnd (first 12 characters of file name).
        String fileName = (String) exchange.getIn().getHeaders().get("CamelFileName");
        String messageIdentND = fileName.substring(0,12);
        
        // Get authorized role for this message's identnd (Nummerierungsbereich).
        IdentND identND = identNDRepository.findByIdentnd(messageIdentND).orElse(null);
        
        if (identND == null) {
            throw new IllegalArgumentException("IdentND not found: " + messageIdentND);
        }
        
        Role roleIdentND = identND.getRole();
     
        // Is user authorized?
        Authentication authentication = (Authentication) exchange.getIn().getHeader(Exchange.AUTHENTICATION);
        
        boolean authorized = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(roleIdentND.getName())) {
                return;
            }
        }
        
        // User is not authorized for sending messages with this IdentND.
        throw new AuthorisationProcessorException("AuthorisationException: " + authentication.getName() 
        + " is not allowed to send files for " + identND.getIdentnd() + ".");        
    }
}
