import json
from Queue import Queue
class DebtGraph:
    def __init__(self, users: list = None, debts: dict = None,name : str = None) -> None:
        if users is not None:
            self.users: list = users
        else:
            self.users = list()
        if debts is not None:
            self.debts: dict = debts
        else:
            self.debts: dict = dict()
        self.name: str = name

    def add_user(self, user) -> bool:
        if user not in self.users:
            self.users.append(user)
            return True
        else:
            print('User is already in')
            return False

    def add_debt(self, debtees, debtor, debt, users=None, debts=None) -> bool:
        if(type(debtees) == list):
            newdebt = debt/(len(debtees))
            for debtee in debtees:
                if (debtee != debtor):
                    if not self.add_debt_helper(debtee, debtor, newdebt, users, debts):
                        return False
        else:
            return self.add_debt_helper(debtees, debtor, debt, users, debts)
                

    def add_debt_helper(self, debtee, debtor, debt, users=None, debts=None) -> bool:
        if users is None:
            users = self.users
        if debts is None:
            debts = self.debts
        result = False
        if debtee in users and debtor in users and debtor != debtee:
            if debtee not in debts.keys():
                debts[debtee] = list()
            # Check if debtee has already debt to debtor
            for i in range(len(debts[debtee])):
                if result:
                    break
                debtor_in_list, debt_in_list = debts[debtee][i]
                if debtor_in_list == debtor:
                    debts[debtee][i] = (debtor, debt_in_list + debt)
                    result = True
            # If not, check if debtor has previous debt to debtee
            if not result:
                if debtor in debts.keys():
                    for i in range(len(debts[debtor])):
                        if result:
                            break
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
                            result = True
            # If debtor has no previous debt to debtee, create new debt
            if not result:
                debts[debtee].append((debtor, debt))
                result = True
            if(debtor in debts.keys() and len(debts[debtor]) == 0):
                debts.pop(debtor)
            if(debtee in debts.keys() and len(debts[debtee]) == 0):
                debts.pop(debtee)
            return result
    def get_users(self):
        return self.users
    def simplify_debts(self):
        users = list(self.users)
        debts = dict(self.debts)
        while True:
            flag = False
            debtees = list(debts.keys())
            for debtee in debtees:
                if flag:
                    break
                parent = dict()
                first_debtors = list(map(lambda x: x[0], debts[debtee]))
                debtors_queue = Queue()
                for debtor in first_debtors:
                    debtors_queue.enqueue(debtor)
                    parent[debtor] = debtee
                while not debtors_queue.isEmpty():
                    if flag:
                        break
                    debtor = debtors_queue.dequeue()
                    if debtor in debts.keys():
                        for debtee2, debt in debts[debtor]:
                            if debtee2 in first_debtors:
                                flag = True
                                tmp_debtee = debtor
                                tmp_debtor = debtee2
                                debt = debts[debtee][first_debtors.index(tmp_debtor)][1]
                                self.add_debt(tmp_debtor, debtee, debt)
                                while tmp_debtee != debtee :
                                    self.add_debt(tmp_debtee, tmp_debtor, debt)
                                    tmp_debtor = tmp_debtee
                                    tmp_debtee = parent[tmp_debtee]
                                self.add_debt(tmp_debtee, tmp_debtor, debt)
                                break
                            else:
                                if debtee2 not in parent.keys():
                                    debtors_queue.enqueue(debtee2)
                                    parent[debtee2] = debtor
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

    def save(self, file_name : str):
        with open(file_name, 'w') as f:
            json.dump(self.debts, f)