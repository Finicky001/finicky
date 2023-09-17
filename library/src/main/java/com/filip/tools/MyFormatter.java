
package com.filip.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord r) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(r.getLevel().getName()).append("] ");
        sb.append("\t").append(r.getLoggerName()).append(" ").append(r.getSourceMethodName()).append(" ");
        sb.append(sdf.format(new Date(r.getMillis()))).append(": ");
        sb.append(r.getMessage()).append("\n");
        return sb.toString();
    }
}

