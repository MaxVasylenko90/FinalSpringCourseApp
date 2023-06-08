package ua.vasylenko.SensorApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name can't be empty! Please create name for sensor")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters!")
    private String name;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
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
}
