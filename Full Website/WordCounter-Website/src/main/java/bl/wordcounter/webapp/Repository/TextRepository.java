/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Repository;

import bl.wordcounter.webapp.Entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Boone
 */
@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {
    
}