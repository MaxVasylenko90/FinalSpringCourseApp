package ua.vasylenko.SensorApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.vasylenko.SensorApp.dto.MeasurementDTO;
import ua.vasylenko.SensorApp.dto.SensorDTO;
import ua.vasylenko.SensorApp.models.Measurement;
import ua.vasylenko.SensorApp.models.Sensor;
import ua.vasylenko.SensorApp.services.SensorService;
import ua.vasylenko.SensorApp.util.MeasurementIssuesException;
import ua.vasylenko.SensorApp.util.SensorErrorResponse;
import ua.vasylenko.SensorApp.util.SensorWasNotCreatedException;

import java.sql.SQLException;

@RestController
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/sensor/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        checkErrors(bindingResult, sensorDTO);
        sensorService.registration(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private static <T> void checkErrors(BindingResult bindingResult, T object) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors())
                sb.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            if (object.getClass().equals(SensorDTO.class))
                throw new SensorWasNotCreatedException(sb.toString());
            else if (object.getClass().equals(MeasurementDTO.class))
                throw new MeasurementIssuesException(sb.toString());
        }
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        return null;
    }

    private <T, V> V convertToSensor(T object) {
        if (object.getClass().equals())
        return modelMapper.map(object, Sensor.class);
    }


    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleRegistrationException(SQLException e) {
        SensorErrorResponse response = new SensorErrorResponse("This name is already exist! Try another one");
        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
