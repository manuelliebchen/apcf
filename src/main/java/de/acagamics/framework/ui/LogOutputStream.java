package de.acagamics.framework.ui;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * OutputStream that pipes output to Logger with specified level.
 */

public class LogOutputStream extends OutputStream {
    private static final Logger LOG = LogManager.getLogger(LogOutputStream.class.getName());

    private final Level level;

    private String mem;

    public LogOutputStream(Level level) {
        this.level = level;
        mem = "";
    }

    @Override
    public void write(int b) throws IOException {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (b & 0xff);
        mem = mem + new String(bytes);

        if (mem.endsWith("\n")) {
            mem = mem.substring (0, mem.length () - 1);
            flush ();
        }
    }

    @Override
    public void flush() throws IOException {
        LOG.log(level, mem);
        mem = "";
    }
}
