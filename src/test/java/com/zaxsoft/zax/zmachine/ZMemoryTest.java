package com.zaxsoft.zax.zmachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

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

    @Test
    public void fetchByte() {
        int size = new Random().nextInt(100) + 2;
        int position = size / 2;
        byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        when(fileLoader.load(anyString())).thenReturn(new ZStory(bytes));
        memory.initialize(userInterface, "");

        int actual = memory.fetchByte(position);

        int expected = bytes[position] & 0xff;
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void fetchByteBelowBounds() {
        int size = new Random().nextInt(100) + 2;
        byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        when(fileLoader.load(anyString())).thenReturn(new ZStory(bytes));
        memory.initialize(userInterface, "");

        memory.fetchByte(-1);

        verify(userInterface).fatal("Memory fault: address -1");
    }

    @Test
    public void fetchByteAboveBounds() {
        int size = new Random().nextInt(100) + 2;
        byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        when(fileLoader.load(anyString())).thenReturn(new ZStory(bytes));
        memory.initialize(userInterface, "");

        memory.fetchByte(bytes.length);

        verify(userInterface).fatal("Memory fault: address " + bytes.length);
    }
}
