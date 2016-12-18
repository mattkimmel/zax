/*
 * Copyright (c) 2008 Matthew E. Kimmel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.zaxsoft.zax.zmachine;

import java.io.*;

class ZMemory {
    private final ZFileLoader fileLoader;
    private final ZUserInterface userInterface;
    private byte[] data;
    private int dataLength;

    ZMemory(ZUserInterface userInterface, ZFileLoader fileLoader) {
        this.fileLoader = fileLoader;
        this.userInterface = userInterface;
    }

    ZMemory(ZUserInterface userInterface, byte[] storyFileBytes) {
        this.userInterface = userInterface;
        this.fileLoader = null;
        this.data = storyFileBytes;
        this.dataLength = storyFileBytes.length;
    }

    // The initialize routine sets things up and loads a game
    // into memory.  It is passed the ZUserInterface object
    // for this ZMachine and the filename of the story-file.
    void initialize(String storyFilePath) {
        ZStory story = fileLoader.load(storyFilePath);

        if (story == ZStory.NOT_FOUND) {
            userInterface.fatal("Story file '" + storyFilePath + "' not found.");
        } else {
            data = story.getStory();
            dataLength = story.getStory().length;
        }
    }

    // Fetch a byte from the specified address
    int fetchByte(int address) {
        if (address < 0 || address > (dataLength - 1)) {
            userInterface.fatal("Memory fault: address " + address);
            return 0;
        } else {
            return (data[address] & 0xff);
        }
    }

    // Store a byte at the specified address
    void putByte(int address, int b) {
        if (address < 0 || address > (dataLength - 1)) {
            userInterface.fatal("Memory fault: address " + address);
        } else {
            data[address] = (byte) (b & 0xff);
        }
    }

    // Fetch a word from the specified address
    int fetchWord(int address) {
        if (address < 0 || address > (dataLength - 2)) {
            userInterface.fatal("Memory fault: address " + address);
            return 0;
        } else {
            return (((data[address] << 8) | (data[address + 1] & 0xff)) & 0xffff);
        }
    }

    // Store a word at the specified address
    void putWord(int address, int word) {
        if (address < 0 || address > (dataLength - 2)) {
            userInterface.fatal("Memory fault: address " + address);
        } else {
            data[address] = (byte) ((word >> 8) & 0xff);
            data[address + 1] = (byte) (word & 0xff);
        }
    }

    // Dump the specified amount of memory, starting at the specified address,
    // to the specified DataOutputStream.
    void dumpMemory(DataOutputStream dos, int address, int length) throws IOException {
        dos.write(data, address, length);
    }

    // Read in memory stored by dumpMemory.
    void readMemory(DataInputStream dis, int address, int length) throws IOException {
        dis.read(data, address, length);
    }
}
