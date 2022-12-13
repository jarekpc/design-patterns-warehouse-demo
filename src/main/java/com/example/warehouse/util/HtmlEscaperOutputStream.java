package com.example.warehouse.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HtmlEscaperOutputStream extends FilterOutputStream {
    public HtmlEscaperOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        if (c == '<') {
            super.write('&');
            super.write('l');
            super.write('t');
            super.write(';');
        } else if (c == '>') {
            super.write('&');
            super.write('g');
            super.write('t');
            super.write(';');
        } else if (c == '&') {
            super.write('&');
            super.write('a');
            super.write('m');
            super.write('p');
            super.write(';');
        } else {
            super.write(b);
        }
    }
}
