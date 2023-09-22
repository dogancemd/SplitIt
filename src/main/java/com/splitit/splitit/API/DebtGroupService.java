package com.splitit.splitit.API;


import com.splitit.splitit.API.DebtRepo;
import com.splitit.splitit.DebtGraph;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtGroupService {
    @Autowired
    private DebtRepo debtgroup;

    public boolean save(DebtGraph dbtgrph){
        this.debtgroup.save(dbtgrph);
        return true;
    }
    public DebtGraph findbygroupname(ObjectId grouId){
        return debtgroup.findDebtGraphByGroupId(grouId);
    }

    public void deleteAllGroups(){
        debtgroup.deleteAll();
    }
}
