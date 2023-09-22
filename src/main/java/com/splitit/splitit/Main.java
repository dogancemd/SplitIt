package com.splitit.splitit;

public class Main {
    public static void main(String[] args){
        DebtGraph dbtgrph = new DebtGraph();
        DebtGraphUser ali= new DebtGraphUser("Ali");
        dbtgrph.addDebtGraphUser(ali);
        DebtGraphUser baris= new DebtGraphUser("Baris");
        dbtgrph.addDebtGraphUser(baris);
        DebtGraphUser cem= new DebtGraphUser("Cem");
        dbtgrph.addDebtGraphUser(cem);
        DebtGraphUser dogan= new DebtGraphUser("Dogan");
        dbtgrph.addDebtGraphUser(dogan);
        dbtgrph.transaction(cem,dogan,40);
        dbtgrph.transaction(baris,ali,100);
        dbtgrph.transaction(cem,ali,50);
        dbtgrph.transaction(dogan,ali,20);
        dbtgrph.transaction(baris,cem,30);
        dbtgrph.transaction(ali,baris,30);
        dbtgrph.transaction(ali,dogan,15);
        dbtgrph.transaction(dogan,baris,30);
        dbtgrph.Print();
        System.out.println("\n\n");
        DebtGraph new_dbtgrph = dbtgrph.simplifyDebts();
        new_dbtgrph.Print();
    }

    public static DebtGraph createTestGraph(){
        DebtGraph dbtgrph = new DebtGraph();
        DebtGraphUser ali= new DebtGraphUser("Ali");
        dbtgrph.addDebtGraphUser(ali);
        DebtGraphUser baris= new DebtGraphUser("Baris");
        dbtgrph.addDebtGraphUser(baris);
        DebtGraphUser cem= new DebtGraphUser("Cem");
        dbtgrph.addDebtGraphUser(cem);
        DebtGraphUser dogan= new DebtGraphUser("Dogan");
        dbtgrph.addDebtGraphUser(dogan);
        dbtgrph.transaction(cem,dogan,40);
        dbtgrph.transaction(baris,ali,100);
        dbtgrph.transaction(cem,ali,50);
        dbtgrph.transaction(dogan,ali,20);
        dbtgrph.transaction(baris,cem,30);
        dbtgrph.transaction(ali,baris,30);
        dbtgrph.transaction(ali,dogan,15);
        dbtgrph.transaction(dogan,baris,30);
        return dbtgrph;
    }
}
