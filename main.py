from DebtGraph import DebtGraph

debtgrph = DebtGraph(users=["Ali", "Banu", "Cem", "Dogan"])
debtgrph.add_debt("Ali", "Banu", 100)
debtgrph.add_debt("Ali", "Cem", 50)
debtgrph.add_debt("Ali", "Dogan", 20)
debtgrph.add_debt("Dogan", "Cem", 40)
debtgrph.add_debt("Cem", "Banu", 30)
debtgrph.add_debt("Banu", "Dogan", 30)
debtgrph.add_debt("Dogan", "Ali", 15)
debtgrph.add_debt("Banu", "Ali", 30)
print(debtgrph)
print("")
print((debtgrph.simplify_debts()))
