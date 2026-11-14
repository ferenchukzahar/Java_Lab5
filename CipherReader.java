package com.lab5.task3;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class CipherReader extends FilterReader {
    private final int keyOffset;

    public CipherReader(Reader in, char keyChar) {
        super(in);
        this.keyOffset = (int) keyChar;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1) ? -1 : (c - keyOffset);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int count = super.read(cbuf, off, len);
        if (count != -1) {
            for (int i = off; i < off + count; i++) {
                cbuf[i] = (char) (cbuf[i] - keyOffset);
            }
        }
        return count;
    }
}
