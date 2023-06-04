package ua.vasylenko.SensorApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import ua.vasylenko.SensorApp.models.Sensor;

public class MeasurementDTO {
    @Min(value = -100)
    @Max(value = 100)
    @NotEmpty
    private double value;

    @NotEmpty
    private boolean isRaining;
    @ManyToOne
    @JoinColumn(name = "sensorId", referencedColumnName = "id")
    @NotEmpty
    private Sensor sensor;

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
