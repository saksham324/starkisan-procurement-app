package com.example.starkisan;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class CSVWriter {
    public static final char DEFAULT_ESCAPE_CHARACTER = '\"';
    public static final String DEFAULT_LINE_END = "\n";
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char NO_ESCAPE_CHARACTER = 0;
    public static final char NO_QUOTE_CHARACTER = 0;
    private char escapechar;
    private String lineEnd;

    /* renamed from: pw */
    private PrintWriter f76pw;
    private char quotechar;
    private char separator;

    public CSVWriter(Writer writer) {
        this(writer, ',', '\"', '\"', "\n");
    }

    public CSVWriter(Writer writer, char separator2, char quotechar2, char escapechar2, String lineEnd2) {
        this.f76pw = new PrintWriter(writer);
        this.separator = separator2;
        this.quotechar = quotechar2;
        this.escapechar = escapechar2;
        this.lineEnd = lineEnd2;
    }

    public void writeNext(String[] nextLine) {
        if (nextLine != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nextLine.length; i++) {
                if (i != 0) {
                    sb.append(this.separator);
                }
                String nextElement = nextLine[i];
                if (nextElement != null) {
                    char c = this.quotechar;
                    if (c != 0) {
                        sb.append(c);
                    }
                    for (int j = 0; j < nextElement.length(); j++) {
                        char nextChar = nextElement.charAt(j);
                        char c2 = this.escapechar;
                        if (c2 == 0 || nextChar != this.quotechar) {
                            char c3 = this.escapechar;
                            if (c3 == 0 || nextChar != c3) {
                                sb.append(nextChar);
                            } else {
                                sb.append(c3);
                                sb.append(nextChar);
                            }
                        } else {
                            sb.append(c2);
                            sb.append(nextChar);
                        }
                    }
                    char c4 = this.quotechar;
                    if (c4 != 0) {
                        sb.append(c4);
                    }
                }
            }
            sb.append(this.lineEnd);
            this.f76pw.write(sb.toString());
        }
    }

    public void flush() throws IOException {
        this.f76pw.flush();
    }

    public void close() throws IOException {
        this.f76pw.flush();
        this.f76pw.close();
    }
}
