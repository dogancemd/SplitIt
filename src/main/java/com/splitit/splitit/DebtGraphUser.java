package com.splitit.splitit;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
public class DebtGraphUser {
    public String name;
    private ObjectId DebtGraphUser_id=new ObjectId();
    private HashMap<ObjectId,Long> to_give=new HashMap<>();
    private HashMap<ObjectId,Long> to_take=new HashMap<>();

    public DebtGraphUser(String name){
        this.name = name;
    }

    public DebtGraphUser(DebtGraphUser rhs){
        this.name = rhs.name;
        this.DebtGraphUser_id = rhs.DebtGraphUser_id;
        this.to_give =rhs.to_give;
        this.to_take = rhs.to_take;
    }
    public ObjectId getDebtGraphUserId(){
        return DebtGraphUser_id;
    }
    public boolean is_indebted(ObjectId debtor){
        return to_give.containsKey(debtor);
    }

    public long debt_amount(ObjectId debtor){
        return to_give.get(debtor);
    }

    public Set<ObjectId> debtorList(){
        return to_give.keySet();
    }
    public void giveMoney(ObjectId debtor,long money){
        if(to_give.containsKey(debtor)){
            long debt = to_give.get(debtor);
            if (debt > money)
                to_give.put(debtor,debt-money);
            else if(debt == money)
                to_give.remove(debtor);
            else{
                to_give.remove(debtor);
                to_take.put(debtor,money-debt);
            }
        }
        else if(to_take.containsKey(debtor)){
            long debt = to_take.get(debtor);
            to_take.put(debtor,debt+money);
        }
        else{
            to_take.put(debtor,money);
        }
    }

    public void takeMoney(ObjectId debtor,long money){
        if(to_take.containsKey(debtor)){
            long debt = to_take.get(debtor);
            if (debt > money)
                to_take.put(debtor,debt-money);
            else if(debt == money)
                to_take.remove(debtor);
            else{
                to_give.put(debtor,money-debt);
                to_take.remove(debtor);
            }
        }
        else if(to_give.containsKey(debtor)){
            long debt = to_give.get(debtor);
            to_give.put(debtor,debt+money);
        }
        else {
            to_give.put(debtor,money);
        }
    }
}