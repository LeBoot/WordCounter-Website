/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.entity.Text;
import bl.wordcounter.webapp.repository.TextRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class TextServiceImpl implements TextService {

    @Autowired
    TextRepository textRepo;
    
    @Override
    public Text saveText(Text text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Text getAText(int id) throws NoSuchElementException {
        Optional<Text> textOpt = textRepo.findById(id);
        return textOpt.orElseThrow();
    }

    @Override
    public List<Text> getAllTextsForAccount(int id) {
        try {
            return textRepo.findAllForAccount(id);
        } catch (Exception e) {
            List<Text> emptyList = new ArrayList<>();
            return emptyList;
        }
    }

    @Override
    public void deleteText(int id) {
        textRepo.deleteById(id);
    }
    
}
