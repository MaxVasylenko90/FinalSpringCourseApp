package ua.vasylenko.SensorApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "value")
    @Min(value = -100)
    @Max(value = 100)
    @NotNull
    private double value;

    @Column(name = "raining")
    @NotNull
    private boolean isRaining;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", referencedColumnName = "id")
    @NotNull
    private Sensor sensor;

    @Column(name = "time")
    private LocalDateTime time;

    public Measurement(double value,  boolean isRaining, Sensor sensor) {
        this.value = value;
        this.isRaining = isRaining;
        this.sensor = sensor;
    }

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
