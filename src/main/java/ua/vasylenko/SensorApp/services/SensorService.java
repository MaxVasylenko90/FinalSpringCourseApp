package ua.vasylenko.SensorApp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vasylenko.SensorApp.models.Sensor;
import ua.vasylenko.SensorApp.repositories.SensorRepository;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    @Transactional
    public void registration(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
