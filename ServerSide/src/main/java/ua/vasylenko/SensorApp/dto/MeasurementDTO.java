package ua.vasylenko.SensorApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import ua.vasylenko.SensorApp.models.Sensor;

public class MeasurementDTO {
    @Min(value = -100)
    @Max(value = 100)
    @NotNull
    private double value;
    @NotNull
    private boolean isRaining;
    @NotNull
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementDTO that)) return false;

        if (Double.compare(that.getValue(), getValue()) != 0) return false;
        if (isRaining() != that.isRaining()) return false;
        return getSensor().equals(that.getSensor());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getValue());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isRaining() ? 1 : 0);
        result = 31 * result + getSensor().hashCode();
        return result;
    }
}
