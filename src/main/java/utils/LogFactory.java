package utils;

import java.util.logging.Logger;

public class LogFactory {
    public static Logger getLogger() {
        return Logger.getLogger(LogFactory.class.getName());
    }
}