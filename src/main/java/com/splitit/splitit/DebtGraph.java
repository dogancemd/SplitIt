package com.splitit.splitit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Set;

@Document("debt-groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebtGraph {
    private HashMap<ObjectId,DebtGraphUser> users = new HashMap<>();

    @Id
    private ObjectId groupId;
    public void transaction(ObjectId DebtGraphUser_to_give, ObjectId DebtGraphUser_to_take, long money){
        users.get(DebtGraphUser_to_give).giveMoney(DebtGraphUser_to_take,money);
        users.get(DebtGraphUser_to_take).takeMoney(DebtGraphUser_to_give,money);
    }

    public void transaction(DebtGraphUser DebtGraphUser_to_give, DebtGraphUser DebtGraphUser_to_take, long money){
        users.get(DebtGraphUser_to_give.getDebtGraphUserId()).giveMoney(DebtGraphUser_to_take.getDebtGraphUserId(),money);
        users.get(DebtGraphUser_to_take.getDebtGraphUserId()).takeMoney(DebtGraphUser_to_give.getDebtGraphUserId(),money);
    }

    public boolean addDebtGraphUser(DebtGraphUser DebtGraphUser_to_add){
        if(users.containsKey(DebtGraphUser_to_add.getDebtGraphUserId()))
            return false;
        else{
            users.put(DebtGraphUser_to_add.getDebtGraphUserId(),DebtGraphUser_to_add);
            return true;
        }
    }

    public void Print(){
        for(ObjectId DebtGraphUserId:users.keySet()){
            System.out.println(users.get(DebtGraphUserId).name);
            for(ObjectId debtorId:users.get(DebtGraphUserId).debtorList()){
                System.out.print("\t");
                System.out.print(users.get(DebtGraphUserId).name);
                System.out.print(" -> ");
                System.out.print(users.get(debtorId).name);
                System.out.print(" : ");
                System.out.print(users.get(DebtGraphUserId).debt_amount(debtorId));
                System.out.println();
            }
        }
    }

    private HashMap<ObjectId,DebtGraphUser> copy_debts(){
        HashMap<ObjectId,DebtGraphUser> new_debts = new HashMap<>();
        for(ObjectId DebtGraphUserId : users.keySet()){
            new_debts.put(DebtGraphUserId,new DebtGraphUser(users.get(DebtGraphUserId)));
        }
        return new_debts;
    }
    public DebtGraph simplifyDebts(){
        HashMap<ObjectId,DebtGraphUser> new_debts = copy_debts();
        while(true){
            boolean flag=false;
            ObjectId id1=new ObjectId(),id2=new ObjectId(),id3=new ObjectId();
            long money=0;
            Set<ObjectId> all_users = new_debts.keySet();
            for(ObjectId DebtGraphUserId1: all_users){
                if (flag)
                        break;
                Set<ObjectId> DebtGraphUser1debtors = new_debts.get(DebtGraphUserId1).debtorList();
                for(ObjectId DebtGraphUserId2: DebtGraphUser1debtors){
                    if (flag)
                        break;
                    Set<ObjectId> DebtGraphUser2debtors = new_debts.get(DebtGraphUserId2).debtorList();
                    for(ObjectId DebtGraphUserId3: DebtGraphUser2debtors){
                        if (flag)
                                break;
                        if(new_debts.get(DebtGraphUserId1).is_indebted(DebtGraphUserId3) || new_debts.get(DebtGraphUserId3).is_indebted(DebtGraphUserId1)){
                            flag=true;
                            money=new_debts.get(DebtGraphUserId2).debt_amount(DebtGraphUserId3);
                            id1=DebtGraphUserId1;
                            id2=DebtGraphUserId2;
                            id3=DebtGraphUserId3;
                            break;
                        }
                    }
                }
            }
            if (flag) {
                transaction(id3, id1, money);
                transaction(id1, id2, money);
                transaction(id2, id3, money);
            }
            else{
                break;
            }
        }
        DebtGraph tmp = new DebtGraph();
        tmp.users = (new_debts);
        return tmp;
    }

    public String toStr(){
        StringBuilder result = new StringBuilder();
        for(ObjectId DebtGraphUserId:users.keySet()){
            result.append(users.get(DebtGraphUserId).name);
            result.append("\n");
            for(ObjectId debtorId:users.get(DebtGraphUserId).debtorList()){
                result.append("|");
                result.append(users.get(DebtGraphUserId).name);
                result.append(" -> ");
                result.append(users.get(debtorId).name);
                result.append(" : ");
                result.append(users.get(DebtGraphUserId).debt_amount(debtorId));
                result.append("|");
            }
        }
        return result.toString();
    }
}