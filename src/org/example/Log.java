package org.example;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Log{
    public Logger logger;
    FileHandler fileHandler;
    public Log(String fileName, String LogName) throws SecurityException, IOException {
        File file = new File(fileName);
        fileHandler = new FileHandler(fileName, true);
        logger = Logger.getLogger(LogName);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter(){
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
            @Override
            public synchronized String format(LogRecord lr){
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getName(),
                        lr.getMessage());
            }
        };
        fileHandler.setFormatter(formatter);
    }
}