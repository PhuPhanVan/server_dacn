
package TranQuocToan.Java.DoAn.utils;

import java.time.Duration;
public class ISO8601Utils {
    public static String formatDuration(String isoDuration) {
        Duration duration = Duration.parse(isoDuration);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
