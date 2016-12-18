package com.zaxsoft.zax.zmachine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ZMemoryTest {
    private final ZFileLoader fileLoader = mock(ZFileLoader.class);
    private final ZUserInterface userInterface = mock(ZUserInterface.class);
    private final ZMemory memory = new ZMemory(fileLoader);

    @Test
    public void signalFileNotFoundToUserInterface() {
        String path = RandomStringUtils.randomAlphanumeric(13);
        when(fileLoader.load(path)).thenReturn(ZStory.NOT_FOUND);

        memory.initialize(userInterface, path);

        verify(userInterface).fatal("Story file '" + path + "' not found.");
    }
}