package ch.so.agi.avgbs.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile() {
        
        return ResponseEntity.ok().header("Content-Type", "text/plain; charset=utf-8").body("Hallo Welt - Post.");
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> login() {
        
        return ResponseEntity.ok().header("Content-Type", "text/plain; charset=utf-8").body("Hallo Welt.");
    }
    
    @RequestMapping(value = "/perform_logout")
    public String logout() {
        return "my-page.html";
    }
    
    

}
