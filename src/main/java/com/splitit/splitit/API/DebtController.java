package com.splitit.splitit.API;


import com.splitit.splitit.DebtGraph;
import com.splitit.splitit.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("debt-group")
public class DebtController {
    @Autowired
    public DebtGroupService debtservice;
    @GetMapping
    public ResponseEntity<String> helloword(){
//        DebtGraph tmp = Main.createTestGraph();
//        boolean cond1 = debtservice.save(tmp);
//        boolean cond2 = debtservice.save(tmp.simplifyDebts());
//        return new ResponseEntity<String>(debtservice.findbygroupname(tmp.getGroupId()).toStr(), HttpStatus.OK);
        debtservice.deleteAllGroups();
        return new ResponseEntity<String>("OK",HttpStatus.OK);
    }

}
