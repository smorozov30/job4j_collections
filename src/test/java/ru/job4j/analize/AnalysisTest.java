package ru.job4j.analize;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalysisTest {
    Analysis.User first;
    Analysis.User second;
    Analysis.User third;
    Analysis.User fourth;
    Analysis.User fifth;

    @Before
    public void beforeTest() {
        first = new Analysis.User();
        first.id = 1;
        first.name = "sergey";
        second = new Analysis.User();
        second.id = 2;
        second.name = "ivan";
        third = new Analysis.User();
        third.id = 3;
        third.name = "vitaly";
        fourth = new Analysis.User();
        fourth.id = 4;
        fourth.name = "andrey";
        fifth = new Analysis.User();
        fifth.id = 5;
        fifth.name = "yurii";
    }

    @Test
    public void whenChanged2Users() {
        List<Analysis.User> previous = Arrays.asList(first, second, third, fourth, fifth);

        Analysis.User secondChanged = new Analysis.User();
        secondChanged.id = 2;
        secondChanged.name = "luba";

        List<Analysis.User> current = Arrays.asList(first, secondChanged, third, fourth, fifth);
        Analysis.Info result = new Analysis().diff(previous, current);
        assertThat(result.changed, is(1));
    }

    @Test
    public void whenDeleted2Users() {
        List<Analysis.User> previous = Arrays.asList(first, second, third, fourth, fifth);
        List<Analysis.User> current = Arrays.asList(first, third, fourth);
        Analysis.Info result = new Analysis().diff(previous, current);
        assertThat(result.deleted, is(2));
    }

    @Test
    public void whenChanged1Deleted1Added3Users() {
        List<Analysis.User> previous = Arrays.asList(first, second, third, fourth, fifth);

        Analysis.User sixthNew = new Analysis.User();
        sixthNew.id = 6;
        sixthNew.name = "alena";
        Analysis.User seventhNew = new Analysis.User();
        seventhNew.id = 7;
        seventhNew.name = "tatiana";
        Analysis.User eighthNew = new Analysis.User();
        eighthNew.id = 8;
        eighthNew.name = "arina";
        Analysis.User secondChanged = new Analysis.User();
        secondChanged.id = 2;
        secondChanged.name = "luba";

        List<Analysis.User> current = Arrays.asList(first, secondChanged, sixthNew, third, seventhNew, fourth, eighthNew);
        Analysis.Info result = new Analysis().diff(previous, current);
        assertThat(result.deleted, is(1));
        assertThat(result.added, is(3));
        assertThat(result.changed, is(1));
    }
}