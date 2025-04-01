package PhanVanPhu.Java.DoAn.utils;

import java.time.Duration;

public class ISO8601Utils {

    public static String formatDuration(String isoDuration) {
        Duration duration = Duration.parse(isoDuration);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static long timeStringToSeconds(String timeString) {
        String[] parts = timeString.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid time format: " + timeString);
        }

        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        double seconds = Double.parseDouble(parts[2]);

        return (hours * 3600) + (minutes * 60) + (long) seconds;
    }

    public static String secondsToISODuration(long totalSeconds) {
        Duration duration = Duration.ofSeconds(totalSeconds);
        StringBuilder sb = new StringBuilder("PT");
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours > 0) {
            sb.append(hours).append("H");
        }
        if (minutes > 0) {
            sb.append(minutes).append("M");
        }
        sb.append(seconds).append("S");
        return sb.toString();
    }
}