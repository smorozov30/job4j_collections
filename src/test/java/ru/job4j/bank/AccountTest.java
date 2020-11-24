package ru.job4j.bank;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {
    @Test
    public void whenTrasfer5000() {
        Account first = new Account(10000.0, "1111");
        Account second = new Account(20000.0, "2222");
        boolean result = first.transfer(second, 5000.0);
        assertThat(result, is(true));
    }
}
