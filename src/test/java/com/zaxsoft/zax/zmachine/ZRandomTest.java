package com.zaxsoft.zax.zmachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ZRandomTest {
    private final ZRandom random = new ZRandom();

    @Test
    public void useSpecifiedSeedToReturnRandomNumber() {
        random.seed(1);
        int result = random.getRandom(10);

        assertThat(result, equalTo(6));
    }

    @Test
    public void respectUpperBound() {
        int result = random.getRandom(1);

        assertThat(result, equalTo(1));
    }
}