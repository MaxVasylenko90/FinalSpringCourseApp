package ua.vasylenko.SensorApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name can't be empty! Please create name for sensor")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters!")
    private String name;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Measurement> measurements;

    public Sensor(String name) {
        this.name = name;
    }

    public Sensor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor sensor)) return false;

        if (getId() != sensor.getId()) return false;
        if (getName() != null ? !getName().equals(sensor.getName()) : sensor.getName() != null) return false;
        return getMeasurements() != null ? getMeasurements().equals(sensor.getMeasurements()) : sensor.getMeasurements() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getMeasurements() != null ? getMeasurements().hashCode() : 0);
        return result;
    }
}
