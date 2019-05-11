package ch.so.agi.avgbs.services;

//import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import ch.so.agi.avgbs.models.IdentND;
//import ch.so.agi.avgbs.repositories.IdentNDRepository;

@Service
@Transactional
public class IdentNDService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private IdentNDRepository identNDRepository;
    
    public void fubar() {
        log.info("********1");

//        Optional<IdentND> identND = identNDRepository.findByIdentnd("SO0200002401");
        
        log.info("********");
//        log.info(identND.get().getMunicipality());
    }
}
