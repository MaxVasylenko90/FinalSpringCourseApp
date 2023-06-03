package ua.vasylenko.SensorApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @Column(name = "name")
    @NotEmpty(message = "Name can't be empty! Please create name for sensor")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
