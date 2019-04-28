package ch.so.agi.avgbs.camel.processors;

import java.util.Collection;
import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthorisationProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Authorisation");
        
        Authentication authentication = (Authentication) exchange.getIn().getHeader(Exchange.AUTHENTICATION);
        
        log.info(authentication.getName());
        log.info(authentication.getAuthorities().toString());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            log.info(authority.getAuthority());

         }

        
        //throw new Exception("AuthorisationException");
    }
}
