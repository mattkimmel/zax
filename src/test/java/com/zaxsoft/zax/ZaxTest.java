package com.zaxsoft.zax;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.zaxsoft.zax.awt.AwtUserInterface;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ZaxTest {
    @Test
    public void callUserInterfaceWithVersion() {
        String version = RandomStringUtils.randomAlphanumeric(25);
        AwtUserInterface userInterface = mock(AwtUserInterface.class);
        new Zax(userInterface).run(version);

        verify(userInterface).start(version);
    }

    @Test
    public void handleNullVersion() {
        AwtUserInterface userInterface = mock(AwtUserInterface.class);
        new Zax(userInterface).run(null);

        verify(userInterface).start("");
    }
}