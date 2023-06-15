package ua.vasylenko.SensorApp.util;

public class SensorWasNotCreatedException extends RuntimeException {
    public SensorWasNotCreatedException(String message) {
        super(message);
    }
}
