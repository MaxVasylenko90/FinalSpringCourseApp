package ua.vasylenko.SensorApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.vasylenko.SensorApp.models.Measurement;

import java.util.Collection;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
   @Query("SELECT m FROM Measurement m WHERE m.isRaining = true")
   List<Measurement> countRainyDays();
}
