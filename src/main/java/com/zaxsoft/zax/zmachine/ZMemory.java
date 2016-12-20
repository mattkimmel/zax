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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ZMemory {
    private final ZUserInterface userInterface;
    private byte[] data;
    private int dataLength;

    ZMemory(ZUserInterface userInterface, byte[] storyFileBytes) {
        this.userInterface = userInterface;
        this.data = storyFileBytes;
        this.dataLength = storyFileBytes.length;
    }

    int fetchByte(int address) {
        if (address < 0 || address > (dataLength - 1)) {
            userInterface.fatal("Memory fault: address " + address);
            return 0;
        } else {
            return (data[address] & 0xff);
        }
    }

    void putByte(int address, int b) {
        if (address < 0 || address > (dataLength - 1)) {
            userInterface.fatal("Memory fault: address " + address);
        } else {
            data[address] = (byte) (b & 0xff);
        }
    }

    int fetchWord(int address) {
        if (address < 0 || address > (dataLength - 2)) {
            userInterface.fatal("Memory fault: address " + address);
            return 0;
        } else {
            return (((data[address] << 8) | (data[address + 1] & 0xff)) & 0xffff);
        }
    }

    void putWord(int address, int word) {
        if (address < 0 || address > (dataLength - 2)) {
            userInterface.fatal("Memory fault: address " + address);
        } else {
            data[address] = (byte) ((word >> 8) & 0xff);
            data[address + 1] = (byte) (word & 0xff);
        }
    }

    void dumpMemory(DataOutputStream outputStream, int address, int length) throws IOException {
        outputStream.write(data, address, length);
    }

    void readMemory(DataInputStream inputStream, int address, int length) throws IOException {
        inputStream.read(data, address, length);
    }
}
