package ua.vasylenko.SensorApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vasylenko.SensorApp.models.Measurement;
import ua.vasylenko.SensorApp.models.Sensor;
import ua.vasylenko.SensorApp.repositories.MeasurementRepository;
import ua.vasylenko.SensorApp.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Measurement measurement, String sensorName) {
        Sensor sensor = sensorRepository.findByName(sensorName).orElse(null);
        measurement.setSensor(sensor);
        measurement.setTime(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public List<Measurement> rainyDaysCount() {
        return measurementRepository.countRainyDays();
    }
}
