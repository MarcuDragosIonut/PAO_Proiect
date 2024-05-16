package Services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final DateTimeFormatter dt_frmt = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    private static final FileWriter fw;

    static {
        try {
            fw = new FileWriter("src/audit.csv", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void audit_action(String message) throws IOException {
        message = message.concat(", ");
        fw.append(message).append(Instant.now().atZone(ZoneId.of("Europe/Bucharest")).format(dt_frmt)).append(String.valueOf('\n'));
    }

    public static void close_file() throws IOException {
        fw.close();
    }
}
