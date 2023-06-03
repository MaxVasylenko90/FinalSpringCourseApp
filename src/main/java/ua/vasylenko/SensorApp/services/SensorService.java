package ua.vasylenko.SensorApp.services;

import org.springframework.stereotype.Service;
import ua.vasylenko.SensorApp.models.Sensor;
import ua.vasylenko.SensorApp.repositories.SensorRepository;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    public void registration(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
