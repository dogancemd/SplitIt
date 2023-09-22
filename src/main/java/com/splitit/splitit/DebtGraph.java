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
    private HashMap<ObjectId,DebtGraphUser> DebtGraphUsers = new HashMap<>();

    @Id
    private ObjectId groupId;
    public void transaction(ObjectId DebtGraphUser_to_give, ObjectId DebtGraphUser_to_take, long money){
        DebtGraphUsers.get(DebtGraphUser_to_give).giveMoney(DebtGraphUser_to_take,money);
        DebtGraphUsers.get(DebtGraphUser_to_take).takeMoney(DebtGraphUser_to_give,money);
    }

    public void transaction(DebtGraphUser DebtGraphUser_to_give, DebtGraphUser DebtGraphUser_to_take, long money){
        DebtGraphUsers.get(DebtGraphUser_to_give.getDebtGraphUserId()).giveMoney(DebtGraphUser_to_take.getDebtGraphUserId(),money);
        DebtGraphUsers.get(DebtGraphUser_to_take.getDebtGraphUserId()).takeMoney(DebtGraphUser_to_give.getDebtGraphUserId(),money);
    }

    public boolean addDebtGraphUser(DebtGraphUser DebtGraphUser_to_add){
        if(DebtGraphUsers.containsKey(DebtGraphUser_to_add.getDebtGraphUserId()))
            return false;
        else{
            DebtGraphUsers.put(DebtGraphUser_to_add.getDebtGraphUserId(),DebtGraphUser_to_add);
            return true;
        }
    }

    public void Print(){
        for(ObjectId DebtGraphUserId:DebtGraphUsers.keySet()){
            System.out.println(DebtGraphUsers.get(DebtGraphUserId).name);
            for(ObjectId debtorId:DebtGraphUsers.get(DebtGraphUserId).debtorList()){
                System.out.print("\t");
                System.out.print(DebtGraphUsers.get(DebtGraphUserId).name);
                System.out.print(" -> ");
                System.out.print(DebtGraphUsers.get(debtorId).name);
                System.out.print(" : ");
                System.out.print(DebtGraphUsers.get(DebtGraphUserId).debt_amount(debtorId));
                System.out.println();
            }
        }
    }

    private HashMap<ObjectId,DebtGraphUser> copy_debts(){
        HashMap<ObjectId,DebtGraphUser> new_debts = new HashMap<>();
        for(ObjectId DebtGraphUserId : DebtGraphUsers.keySet()){
            new_debts.put(DebtGraphUserId,new DebtGraphUser(DebtGraphUsers.get(DebtGraphUserId)));
        }
        return new_debts;
    }
    public DebtGraph simplifyDebts(){
        HashMap<ObjectId,DebtGraphUser> new_debts = copy_debts();
        while(true){
            boolean flag=false;
            ObjectId id1=new ObjectId(),id2=new ObjectId(),id3=new ObjectId();
            long money=0;
            Set<ObjectId> all_DebtGraphUsers = new_debts.keySet();
            for(ObjectId DebtGraphUserId1: all_DebtGraphUsers){
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
        tmp.DebtGraphUsers = (new_debts);
        return tmp;
    }

    public String toStr(){
        StringBuilder result = new StringBuilder();
        for(ObjectId DebtGraphUserId:DebtGraphUsers.keySet()){
            result.append(DebtGraphUsers.get(DebtGraphUserId).name);
            result.append("\n");
            for(ObjectId debtorId:DebtGraphUsers.get(DebtGraphUserId).debtorList()){
                result.append("|");
                result.append(DebtGraphUsers.get(DebtGraphUserId).name);
                result.append(" -> ");
                result.append(DebtGraphUsers.get(debtorId).name);
                result.append(" : ");
                result.append(DebtGraphUsers.get(DebtGraphUserId).debt_amount(debtorId));
                result.append("|");
            }
        }
        return result.toString();
    }
}