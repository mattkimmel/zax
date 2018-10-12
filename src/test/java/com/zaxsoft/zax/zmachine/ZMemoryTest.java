package com.zaxsoft.zax.zmachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ZMemoryTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static final int MEMORY_SIZE = 100;
    private static final int BYTE_LIMIT = 256;
    private static final int WORD_LIMIT = 65536;
    private final ZUserInterface userInterface = mock(ZUserInterface.class);

    @Test
    public void fetchByte() {
        int expected = new Random().nextInt(BYTE_LIMIT);
        int position = new Random().nextInt(MEMORY_SIZE);
        ZMemory memory = createMemory(position, expected);

        int actual = memory.fetchByte(position);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void fetchByteBelowBounds() {
        ZMemory memory = createMemory();

        memory.fetchByte(-1);

        verify(userInterface).fatal("Memory fault: address -1");
    }

    @Test
    public void fetchByteAboveBounds() {
        ZMemory memory = createMemory();

        memory.fetchByte(MEMORY_SIZE);

        verify(userInterface).fatal("Memory fault: address " + MEMORY_SIZE);
    }

    @Test
    public void putByte() {
        ZMemory memory = createMemory();
        int position = new Random().nextInt(MEMORY_SIZE);
        int expected = new Random().nextInt(BYTE_LIMIT);

        memory.putByte(position, expected);

        int actual = memory.fetchByte(position);
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void putByteBelowBounds() {
        ZMemory memory = createMemory();

        memory.putByte(-1, new Random().nextInt(BYTE_LIMIT));

        verify(userInterface).fatal("Memory fault: address -1");
    }

    @Test
    public void putByteAboveBounds() {
        ZMemory memory = createMemory();

        memory.putByte(MEMORY_SIZE, new Random().nextInt(BYTE_LIMIT));

        verify(userInterface).fatal("Memory fault: address " + MEMORY_SIZE);
    }

    @Test
    public void fetchWord() {
        int expected = new Random().nextInt(WORD_LIMIT);
        int position = new Random().nextInt(MEMORY_SIZE - 1);
        ZMemory memory = createMemoryWord(position, expected);

        int actual = memory.fetchWord(position);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void fetchWordBelowBounds() {
        ZMemory memory = createMemory();

        memory.fetchWord(-1);

        verify(userInterface).fatal("Memory fault: address -1");
    }

    @Test
    public void fetchWordAboveBounds() {
        ZMemory memory = createMemory();

        memory.fetchWord(MEMORY_SIZE  - 1);

        verify(userInterface).fatal("Memory fault: address " + (MEMORY_SIZE - 1));
    }

    @Test
    public void putWord() {
        ZMemory memory = createMemory();
        int position = new Random().nextInt(MEMORY_SIZE - 1);
        int expected = new Random().nextInt(WORD_LIMIT);

        memory.putWord(position, expected);

        int actual = memory.fetchWord(position);
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void putWordBelowBounds() {
        ZMemory memory = createMemory();

        memory.putWord(-1, new Random().nextInt(WORD_LIMIT));

        verify(userInterface).fatal("Memory fault: address -1");
    }

    @Test
    public void putWordAboveBounds() {
        ZMemory memory = createMemory();

        memory.putWord(MEMORY_SIZE - 1, new Random().nextInt(WORD_LIMIT));

        verify(userInterface).fatal("Memory fault: address " + (MEMORY_SIZE - 1));
    }

    @Test
    public void writeMemory() throws IOException {
        byte[] bytes = createBytes();
        ZMemory memory = new ZMemory(userInterface, bytes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(out);
        memory.dumpMemory(outputStream, 0, MEMORY_SIZE);

        assertThat(out.toByteArray(), equalTo(bytes));
    }

    @Test
    public void readMemory() throws IOException {
        byte[] bytes = createBytes();
        ZMemory memory = new ZMemory(userInterface, createBytes());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        memory.readMemory(new DataInputStream(inputStream), 0, MEMORY_SIZE);

        for (int i = 0; i < MEMORY_SIZE;i++) {
            int expected = bytes[i] & 0xff;
            assertThat(memory.fetchByte(i), equalTo(expected));
        }
    }

    private ZMemory createMemory() {
        return createMemory(new Random().nextInt(MEMORY_SIZE), new Random().nextInt(BYTE_LIMIT));
    }

    private ZMemory createMemory(int address, int expected) {
        byte[] bytes = createBytes();
        bytes[address] = (byte) (expected & 0xff);
        return new ZMemory(userInterface, bytes);
    }

    private ZMemory createMemoryWord(int address, int word) {
        byte[] bytes = createBytes();
        bytes[address] = (byte) ((word >> 8) & 0xff);
        bytes[address + 1] = (byte) (word & 0xff);
        return new ZMemory(userInterface, bytes);
    }

    private byte[] createBytes() {
        byte[] bytes = new byte[MEMORY_SIZE];
        new Random().nextBytes(bytes);
        return bytes;
    }
}
