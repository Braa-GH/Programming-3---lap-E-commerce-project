package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance = null;
    private Logger() throws IOException {}
    public static synchronized Logger getInstance() throws IOException {
        if (instance == null){
            instance = new Logger();
        }
        return instance;
    }


    private File logFile = new File("log.txt");
    LocalDateTime now = LocalDateTime.now();

    public void log(String data) throws IOException {
        FileWriter writer = new FileWriter(logFile, true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy] [HH:mm:ss]");
        String time = now.format(formatter);
        writer.append("[" + time + "] " + data + "\n");
        writer.close();
    }
}
