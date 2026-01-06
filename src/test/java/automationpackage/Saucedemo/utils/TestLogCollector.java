package automationpackage.Saucedemo.utils;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestLogCollector {

    private static final List<String> logs = new CopyOnWriteArrayList<>();

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private TestLogCollector() {}

    public static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String thread = Thread.currentThread().getName();
        logs.add(String.format(
                "%s [%s] %-5s %s",
                timestamp, thread, level, message
        ));
    }

    public static void flushToFile() {
        try {
            Path logDir = Paths.get("target", "logs");
            Files.createDirectories(logDir);

            Path logFile = logDir.resolve("execution.log");

            Files.write(
                    logFile,
                    logs,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            logs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static byte[] getLogs() {
		// TODO Auto-generated method stub
		return null;
	}
}
