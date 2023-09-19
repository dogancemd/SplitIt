package com.splitit.splitit;

import org.bson.types.ObjectId;

import java.util.HashMap;


public class DebtGraph {
    private HashMap<ObjectId,User> users = new HashMap<>();

    public void transaction(ObjectId user_to_give, ObjectId user_to_take, long money){
        users.get(user_to_give).giveMoney(user_to_take,money);
        users.get(user_to_take).takeMoney(user_to_give,money);
    }

    public boolean addUser(User user_to_add){
        if(users.containsKey(user_to_add.getUserId()))
            return false;
        else{
            users.put(user_to_add.getUserId(),user_to_add);
            return true;
        }
    }

    public void Print(){

    }

    public void simplifyDebts(){
        while(true){
            boolean flag=false;
            for(ObjectId userId1: users.keySet()){
                if (flag)
                        break;
                for(ObjectId userId2: users.get(userId1).debtorList()){
                    if (flag)
                        break;
                    for(ObjectId userId3: users.get(userId2).debtorList()){
                        if(users.get(userId3).is_indebted(userId1)){
                            flag=true;
                            long money=users.get(userId3).debt_amount(userId1);
                            transaction(userId3,userId1,money);
                            transaction(userId1,userId2,money);
                            transaction(userId2,userId3,money);
                            break;
                        }
                    }
                }
            }
            if (!flag)
                break;
        }
    }
}