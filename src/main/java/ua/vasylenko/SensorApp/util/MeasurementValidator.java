package ua.vasylenko.SensorApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.vasylenko.SensorApp.dto.MeasurementDTO;
import ua.vasylenko.SensorApp.services.SensorService;

@Component
public class MeasurementValidator implements Validator{
        private final SensorService sensorService;
        @Autowired
        public MeasurementValidator(SensorService sensorService) {
            this.sensorService = sensorService;
        }
        @Override
        public boolean supports(Class<?> clazz) {
            return MeasurementDTO.class.equals(clazz);
        }
        @Override
        public void validate(Object target, Errors errors) {
            MeasurementDTO measurementDTO = (MeasurementDTO) target;
            if (sensorService.findByName(measurementDTO.getSensor().getName()).isEmpty())
                errors.rejectValue("name", "", "Sensor with this name is not exist!");
        }

}
