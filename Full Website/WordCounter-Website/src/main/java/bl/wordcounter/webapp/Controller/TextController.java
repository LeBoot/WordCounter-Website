/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Controller;

import bl.wordcounter.webapp.Service.AnalysisService;
import bl.wordcounter.webapp.Service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    @RequestMapping(value = "/display-text", method = RequestMethod.GET)
    public String displayText() {
        return "Display Text.";
    }
    
    
    //ANALYSIS =================================================================
    
    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public String analyze() {
        return "Analyze.";
    }

}
