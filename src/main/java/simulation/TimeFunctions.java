package simulation;

public class TimeFunctions {
    public static String toHumanTime(double simulationTime) {
        int day = (int) (simulationTime / (8 * 60 * 60 * 1000));
        int hour = (int) (simulationTime / 60 / 60 / 1000) % 8;
        int minute = (int) (simulationTime / 60 / 1000) % 60;
        int second = (int) (simulationTime / 1000) % 60;
        return String.format("day:%02d time:%02d:%02d:%02d", day, hour, minute, second);
    }
}
