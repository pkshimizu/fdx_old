package net.noncore.fdx.common.utils;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CastTest {

    @Test
    public void testWhenValueReturnValue() {
        String actual = Cast.of("test")
                .when(Integer.class, "Integer")
                .when(String.class, "String")
                .other("Other")
                .end();
        assertThat(actual, is("String"));
    }

    @Test
    public void testWhenOtherReturnValue() {
        String actual = Cast.of(1.1)
                .when(Integer.class, "Integer")
                .when(String.class, "String")
                .other("Other")
                .end();
        assertThat(actual, is("Other"));
    }

    @Test
    public void testWhenConditionValueReturnExpr() {
        int actual = Cast.of(1)
                .when(Integer.class, v -> v + 10)
                .when(String.class, v -> v + 20)
                .other(v -> 40)
                .end();
        assertThat(actual, is(11));
    }

    @Test
    public void testWhenConditionValueReturnExpr2() {
        int actual = Cast.of("111")
                .when(Integer.class, v -> v + 10)
                .when(String.class, v -> v.length() + 20)
                .other(v -> 40)
                .end();
        assertThat(actual, is(23));
    }

    @Test
    public void testOtherReturnExpr() {
        int actual = Cast.of(1L)
                .when(Integer.class, v -> v + 10)
                .when(String.class, v -> v + 20)
                .other(v -> 40)
                .end();
        assertThat(actual, is(40));
    }

    @Test
    public void testWhenConditionValueReturnVoid() {
        Date date = new Date();
        long time = date.getTime();
        Cast.of(date)
                .whenCall(Integer.class, v -> fail())
                .whenCall(String.class, v -> fail())
                .whenCall(Date.class, v -> assertThat(v.getTime(), is(time)))
                .otherCall(v -> fail())
                .end();
    }

    @Test
    public void testOtherReturnVoid() {
        Date date = new Date();
        Cast.of(date)
                .whenCall(Integer.class, v -> fail())
                .whenCall(String.class, v -> fail())
                .otherCall(v -> assertThat(v, is(date)))
                .end();
    }
}
