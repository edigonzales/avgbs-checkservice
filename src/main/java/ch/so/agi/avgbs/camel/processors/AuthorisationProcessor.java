package ch.so.agi.avgbs.camel.processors;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import ch.so.agi.avgbs.models.IdentND;
import ch.so.agi.avgbs.repositories.IdentNDRepository;
import ch.so.agi.avgbs.services.CustomUserDetailsService;
import ch.so.agi.avgbs.services.IdentNDService;

@Component
public class AuthorisationProcessor implements Processor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IdentNDService identNDService;
    
    @Autowired
    private IdentNDRepository identNDRepository;
        
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Authorisation");
        
        Authentication authentication = (Authentication) exchange.getIn().getHeader(Exchange.AUTHENTICATION);
        
        log.info(authentication.getName());
        log.info(authentication.getAuthorities().toString());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            log.info("=====");
            log.info(authority.getAuthority());
        }
        
        
        
        log.info("-----------------------------------2");
        IdentND identND = identNDRepository.findByIdentnd("SO0200002401").orElse(null);
        
        log.info(identND.getMunicipality());
        log.info(identND.getRole().getName());
        log.info("***********************************");

        
        //throw new Exception("AuthorisationException");
    }
}
