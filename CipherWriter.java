package com.lab5.task3;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class CipherWriter extends FilterWriter {
    private final int keyOffset;

    public CipherWriter(Writer out, char keyChar) {
        super(out);
        this.keyOffset = (int) keyChar;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + keyOffset);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] encrypted = new char[len];
        for (int i = 0; i < len; i++) {
            encrypted[i] = (char) (cbuf[off + i] + keyOffset);
        }
        super.write(encrypted, 0, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }
}
