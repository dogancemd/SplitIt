class DebtGraph:
    def __init__(self, users: list = None, debts: dict = None) -> None:
        if users is not None:
            self.users: list = users
        else:
            self.users = list()
        if debts is not None:
            self.debts: dict = debts
        else:
            self.debts: dict = dict()

    def add_user(self, user) -> bool:
        if user not in self.users:
            self.users.append(user)
            return True
        else:
            print('User is already in')
            return False

    def add_debt(self, debtee, debtor, debt, users=None, debts=None) -> bool:
        if users is None:
            users = self.users
        if debts is None:
            debts = self.debts
        if debtee in users and debtor in users and debtor != debtee:
            if debtee not in debts.keys():
                debts[debtee] = list()
            # Check if debtee has already debt to debtor
            for i in range(len(debts[debtee])):
                debtor_in_list, debt_in_list = debts[debtee][i]
                if debtor_in_list == debtor:
                    debts[debtee][i] = (debtor, debt_in_list + debt)
                    return True
            # If not, check if debtor has previous debt to debtee
            if debtor in debts.keys():
                for i in range(len(debts[debtor])):
                    debtor_in_list, debt_in_list = debts[debtor][i]
                    if debtor_in_list == debtee:
                        if debt_in_list > debt:
                            debts[debtor][i] = (debtee, debt_in_list - debt)
                        elif debt > debt_in_list:
                            debt -= debt_in_list
                            debts[debtor].pop(i)
                            debts[debtee].append((debtor, debt))
                        else:
                            debts[debtor].pop(i)
                        return True
            # If debtor has no previous debt to debtee, create new debt
            debts[debtee].append((debtor, debt))
            return True

    def simplify_debts(self):
        users = list(self.users)
        debts = dict(self.debts)
        debtees = debts.keys()
        while True:
            flag = False
            for user1 in debtees:
                if flag:
                    continue
                for user2, debt1 in debts[user1]:
                    if flag:
                        break
                    if user2 in debtees:
                        for user3, debt2 in debts[user2]:
                            if flag:
                                break
                            if user3 in [debtor for debtor, debt in debts[user1]]:
                                self.add_debt(user3, user2, debt2, users, debts)
                                self.add_debt(user2, user1, debt2, users, debts)
                                self.add_debt(user1, user3, debt2, users, debts)
                                flag = True
                                break
            if not flag:
                break
        return DebtGraph(users, debts)

    def __str__(self) -> str:
        out: str = ""
        for debtee in self.debts.keys():
            out += (str(debtee) + " :\n")
            for debtor, debt in self.debts[debtee]:
                out += ("\t" + str(debtee) + " -> " + str(debtor) + " : " + str(debt) + " \n")
        return out

    def __repr__(self) -> str:
        return str(self)
