package com.splitit.splitit;

import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class User {
    public String name;
    private ObjectId user_id=new ObjectId();;
    private HashMap<ObjectId,Long> to_give=new HashMap<ObjectId,Long>();
    private HashMap<ObjectId,Long> to_take=new HashMap<ObjectId,Long>();

    public User(String name){
        this.name = name;
    }

    public ObjectId getUserId(){
        return user_id;
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
            else
                to_take.put(debtor,money-debt);
        }
        else {
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
            else
                to_give.put(debtor,money-debt);
        }
        else {
            to_give.put(debtor,money);
        }
    }
}