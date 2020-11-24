package ru.job4j.bank;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BankTest {
    private Bank bank;
    private User firstUser;
    private String passportFirstUser;
    private User secondUser;
    private String passportSecondUser;
    private Account firstAccount;
    private Account secondAccount;

    @Before
    public void createFirstUsersWithAccounts() {
        this.bank = new Bank();
        this.firstUser = new User("Sergei", "0000 000000");
        this.secondUser = new User("Boris", "1111 111111");
        this.passportFirstUser = this.firstUser.getPassport();
        this.passportSecondUser = this.secondUser.getPassport();
        this.bank.addUser(this.firstUser);
        this.bank.addUser(this.secondUser);
        this.firstAccount = new Account(10000.0, "1111");
        this.secondAccount = new Account(20000.0, "2222");
        this.bank.addAccountToUser(this.passportFirstUser, this.firstAccount);
        this.bank.addAccountToUser(this.passportFirstUser, this.secondAccount);
        this.bank.addAccountToUser(this.passportSecondUser, this.firstAccount);
        this.bank.addAccountToUser(this.passportSecondUser, this.secondAccount);
    }
    @Test
    public void whenAddNewUser() {
        List<Account> list = this.bank.getUserAccounts(this.passportFirstUser);
        int result = list.size();
        assertThat(result, is(2));
    }
    @Test
    public void whenAddNewAccountToUser() {
        List<Account> list = this.bank.getUserAccounts(this.passportFirstUser);
        double result = list.get(0).getValue();
        assertThat(result, is(10000.0));
    }
    @Test
    public void whenGetListAccountsFromUser() {
        List<Account> list = this.bank.getUserAccounts(this.passportFirstUser);
        int result = list.size();
        assertThat(result, is(2));
    }
    @Test
    public void whenTransferBetweenUsers() {
        boolean result = this.bank.transferMoney(this.passportFirstUser, "1111", this.passportSecondUser, "2222", 5000.0);
        assertThat(result, is(true));
    }
    @Test
    public void whenTransferBetweenAccounts() {
        boolean result = this.bank.transferMoney(this.passportFirstUser, "1111", this.passportFirstUser, "2222", 5000.0);
        assertThat(result, is(true));
    }
    @Test
    public void whenDeleteUser() {
        this.bank.deleteUser(this.firstUser);
        List<Account> result = this.bank.getUserAccounts(this.passportFirstUser);
        List<Account> expected = null;
        assertThat(result, is(expected));
    }
    @Test
    public void whenDeleteAccount() {
        this.bank.deleteAccountFromUser(this.passportFirstUser, this.firstAccount);
        List<Account> list = this.bank.getUserAccounts(this.passportFirstUser);
        int result = list.size();
        assertThat(result, is(1));
    }
}
