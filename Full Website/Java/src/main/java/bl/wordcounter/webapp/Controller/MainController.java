/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Controller;

import bl.wordcounter.webapp.Service.ServiceLayerImpl;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Boone
 */
@Controller
public class MainController {
    
    @Autowired
    ServiceLayerImpl service;
    
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    String displayHomepage() {
        return "home";
    }
    
    @RequestMapping(value = "/analyze-1", method = RequestMethod.POST)
    ResponseEntity<Object> getResultsAlphabetically(@RequestParam("text-to-analyze") String input) {
        Map<String, Integer> map = service.getResultsAlphabetically(input);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/analyze-2", method = RequestMethod.POST)
    ResponseEntity<Object> getResultsByOccurance(@RequestParam("form1-text-area") String input) {
        List<Entry<String, Integer>> list = service.getResultsByOccurance(service.getResultsAlphabetically(input));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
}