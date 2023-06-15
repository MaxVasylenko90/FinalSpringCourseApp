package ua.vasylenko.SensorApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ua.vasylenko.SensorApp.models.Measurement;
import ua.vasylenko.SensorApp.models.Sensor;

import java.util.List;

public class SensorDTO {
    @NotEmpty(message = "Name can't be empty! Please create name for sensor")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorDTO sensorDTO)) return false;

        return getName() != null ? getName().equals(sensorDTO.getName()) : sensorDTO.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
