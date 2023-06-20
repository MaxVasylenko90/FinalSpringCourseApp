package ua.vasylenko.SensorApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ua.vasylenko.SensorApp.models.Measurement;
import ua.vasylenko.SensorApp.models.Sensor;
@Component
public class ErrorsUtil {
    private final SensorValidator sensorValidator;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public ErrorsUtil(SensorValidator sensorValidator, MeasurementValidator measurementValidator) {
        this.sensorValidator = sensorValidator;
        this.measurementValidator = measurementValidator;
    }

    public <T> void checkErrors(BindingResult bindingResult, T object) {
        if (object.getClass().equals(Sensor.class))
            sensorValidator.validate(object, bindingResult);
        else if (object.getClass().equals(Measurement.class))
            measurementValidator.validate(object, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors())
                sb.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            throw new SensorOrMeasurementIssuesException(sb.toString());
        }
    }
}
