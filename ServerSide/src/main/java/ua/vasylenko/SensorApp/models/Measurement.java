package ua.vasylenko.SensorApp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value")
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double value;
    @NotNull
    @Column(name = "raining")
    private Boolean isRaining;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", referencedColumnName = "id")
    @NotNull
    private Sensor sensor;

    @Column(name = "time")
    private LocalDateTime time;

    public Measurement(Double value,  Boolean isRaining, Sensor sensor) {
        this.value = value;
        this.isRaining = isRaining;
        this.sensor = sensor;
    }

    public Measurement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return isRaining;
    }

    public void setRaining(Boolean raining) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement that)) return false;

        if (getId() != that.getId()) return false;
        if (Double.compare(that.getValue(), getValue()) != 0) return false;
        if (isRaining() != that.isRaining()) return false;
        if (!getSensor().equals(that.getSensor())) return false;
        return getTime() != null ? getTime().equals(that.getTime()) : that.getTime() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isRaining() ? 1 : 0);
        result = 31 * result + getSensor().hashCode();
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        return result;
    }
}
