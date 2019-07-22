package net.noncore.fdx.common.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CaseTest {

    @Test
    public void testWhenConditionValueReturnValue() {
        String actual = Case.of(1)
                .when(1).then("one")
                .when(2).then("two")
                .when(3).then("three")
                .other().then("other")
                .end();
        assertThat(actual, is("one"));
    }

    @Test
    public void testOtherReturnValue() {
        String actual = Case.of(4)
                .when(1).then("one")
                .when(2).then("two")
                .when(3).then("three")
                .other().then("other")
                .end();
        assertThat(actual, is("other"));
    }

    @Test
    public void textWhenConditionExprReturnValue() {
        String actual = Case.of(1)
                .when(v -> v % 2 == 0).then("Even")
                .when(v -> v % 2 == 1).then( "Odd")
                .end();
        assertThat(actual, is("Odd"));
    }

    @Test
    public void textWhenConditionNullReturnValue() {
        Integer value = null;
        String actual = Case.of(value)
                .whenNull().then("Null")
                .when(v -> v % 2 == 1).then("Odd")
                .when(v -> v % 2 == 0).then("Even")
                .end();
        assertThat(actual, is("Null"));
    }

    @Test
    public void testWhenConditionValueReturnExpr() {
        int actual = Case.of(2)
                .when(1).then(v -> v + 10)
                .when(2).then(v -> v + 20)
                .when(3).then(v -> v + 30)
                .other().then(40)
                .end();
        assertThat(actual, is(22));
    }

    @Test
    public void testOtherReturnExpr() {
        int actual = Case.of(4)
                .when(1).then(v -> v + 10)
                .when(2).then(v -> v + 20)
                .when(3).then(v -> v + 30)
                .other().then(v -> v + 40)
                .end();
        assertThat(actual, is(44));
    }

    @Test
    public void textWhenConditionExprReturnExpr() {
        int actual = Case.of(3)
                .when(v -> v % 2 == 0).then(v -> v / 2)
                .when(v -> v % 2 == 1).then(v -> (v + 1) / 2)
                .end();
        assertThat(actual, is(2));
    }

    @Test
    public void textWhenConditionNullReturnExpr() {
        Integer value = null;
        int actual = Case.of(value)
                .whenNull().then(v -> 10 * 5)
                .when(v -> v % 2 == 0).then(v -> v / 2)
                .when(v -> v % 2 == 1).then(v -> (v + 1) / 2)
                .end();
        assertThat(actual, is(50));
    }

    @Test
    public void testWhenConditionValueReturnVoid() {
        Case.of(2)
                .when(1).call(v -> assertThat(v, is(2)))
                .when(2).call(v -> assertThat(v, is(2)))
                .when(3).call(v -> assertThat(v, is(2)))
                .other().call(v -> assertThat(v, is(2)))
                .end();
    }

    @Test
    public void testOtherReturnVoid() {
        Case.of(2)
                .when(1).call(v -> assertThat(v, is(2)))
                .when(2).call(v -> assertThat(v, is(2)))
                .when(3).call(v -> assertThat(v, is(2)))
                .other().call(v -> assertThat(v, is(2)))
                .end();
    }

    @Test
    public void textWhenConditionExprReturnVoid() {
        Case.of(3)
                .when(v -> v % 2 == 0).call(v -> assertThat(v, is(3)))
                .when(v -> v % 2 == 1).call(v -> assertThat(v, is(3)))
                .end();
    }

    @Test
    public void textWhenConditionNullReturnVoid() {
        Integer value = null;
        Case.of(value)
                .whenNull().call(v -> assertNull(v))
                .when(v -> v % 2 == 0).call(v -> assertNull(v))
                .when(v -> v % 2 == 1).call(v -> assertNull(v))
                .end();
    }

    @Test
    public void textWhenMultiCondition() {
        String actual = Case.of(1)
                .when(1, 2).then("One or Two")
                .when(3).then("Three")
                .end();
        assertThat(actual, is("One or Two"));
    }

    @Test
    public void testSample() {
        int actual = Case.of("hello world")
                .whenNull().then(0)
                .when(v -> v.isEmpty()).then(0)
                .other().then(v -> v.length())
                .end();
        assertThat(actual, is(11));
    }
}
