package ua.vasylenko.SensorApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vasylenko.SensorApp.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

}
