/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Controller;

import bl.wordcounter.webapp.Service.AnalysisService;
import bl.wordcounter.webapp.Service.TextService;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/text")
public class TextController {
    
    @Autowired
    TextService textService;
    
    @Autowired
    AnalysisService analysisService;
    
    
    //CRUD =====================================================================
    
    @RequestMapping(value = "/save-text", method = RequestMethod.POST)
    public String saveText() {
        return "Save Text";
    }
    
    @RequestMapping(value = "/delete-text", method = RequestMethod.POST)
    public String deleteText() {
        return "Delete Text.";
    }
    
    
    //ANALYSIS =================================================================
    
    //AJAX
    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public ResponseEntity<Object> analyze(@RequestParam("text-to-analyze") String input) {
        List<Map.Entry<String, Integer>> list = analysisService.analyze(input);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}