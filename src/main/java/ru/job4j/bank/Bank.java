package ru.job4j.bank;

import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private Map<User, List<Account>> users = new HashMap<>();

    /**
     * Метод добавляет клиента в банк.
     * @param user - Клиент.
     */
    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Метод удаляет клиента из банка.
     * @param user - Клиент, которого нужно удалить.
     */
    public void deleteUser(User user) {
        this.users.remove(user);
    }

    /**
     * Метод добавляет счет клиенту.
     * @param passport - Паспорт клиента, которону требуется добавить счет.
     * @param account - новый счет.
     */
    public void addAccountToUser(String passport, Account account) {
        this.findAccountsByPassport(passport).add(account);
    }

    /**
     * Метод удаляет счет клиента.
     * @param passport - Паспорт клиента у которого удаляем счет.
     * @param account - Счет, который нужно удалить.
     */
    public void deleteAccountFromUser(String passport, Account account) {
        this.findAccountsByPassport(passport).remove(account);
    }

    /**
     * Метод возвращает список банковских счетов клиента.
     * @param passport - Паспорт юзера, список банковских счетов которого мы хотим получить.
     * @return список счетов.
     */
    public List<Account> getUserAccounts(String passport) {
        return this.findAccountsByPassport(passport);
    }

    /**
     * Метод переводит деньги с одного счета на другой.
     * @param srcPassport - Паспорт юзера, у которого списываем деньги.
     *                      По этому параметру получаю список банковских счетов юзера из метода getUserAccounts.
     * @param srcRequisite - По реквизитам нахожу в списке банковских счетов юзера - счет с которого списываем деньги.
     * @param destPassport - Паспорт юзера, которому зачисляем деньги.
     *                      По этому параметру получаю список банковских счетов юзера из метода getUserAccounts.
     * @param dstRequisite - По реквизитам нахожу в списке банковских счетов юзера - счет на которого зачисляем деньги.
     * @param amount - количество денег к списанию/зачислению.
     * @return - true - если операция перечисления удалась, иначе false.
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport,  String dstRequisite, double amount) {
        boolean result = false;
        Account srcAccount = this.findAccount(srcPassport, srcRequisite);
        Account destAccount = this.findAccount(destPassport, dstRequisite);
        if (srcAccount != null && destAccount != null) {
            double srcValue = srcAccount.getValue();
            if (srcValue > amount) {
                srcAccount.transfer(destAccount, amount);
                result = true;
            }
        }
        return result;
    }

    /**
     * Метод возвращает список счетов клиента.
     * @param passport - паспорт клиента для поиска.
     * @return - список счетов клиента, или null, если клиент не зарегистрирован в банке.
     */
    private List<Account> findAccountsByPassport(String passport) {
        List<Account> accountList = null;
        for (Map.Entry<User, List<Account>> pair : users.entrySet()) {
            User user = pair.getKey();
            if (user.getPassport().equals(passport)) {
                accountList = pair.getValue();
            }
        }
        return accountList;
    }

    /**
     * Метод находит счет пользователя по паспорту клиента и реквизитам счета.
     * @param passport - паспорт клиента.
     * @param requisite - реквизиты счте.
     * @return - возвращает счет клиента, или null - если счет не найден.
     */
    private Account findAccount(String passport, String requisite) {
        List<Account> list = this.getUserAccounts(passport).stream().filter(account -> account.getRequisites().
                                                                    equals(requisite)).collect(Collectors.toList());
        Account found = null;
        if (!list.isEmpty()) {
            found = list.get(0);
        }
        return found;
    }
}
