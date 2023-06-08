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
import ua.vasylenko.SensorApp.services.MeasurementService;
import ua.vasylenko.SensorApp.services.SensorService;
import ua.vasylenko.SensorApp.util.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class MainController {
    private final SensorService sensorService;
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MainController(SensorService sensorService, MeasurementService measurementService, ModelMapper modelMapper, SensorValidator sensorValidator, MeasurementValidator measurementValidator) {
        this.sensorService = sensorService;
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
        this.measurementValidator = measurementValidator;
    }

    @PostMapping("/sensor/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        checkErrors(bindingResult, sensorDTO);
        sensorService.registration(modelMapper.map(sensorDTO, Sensor.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        checkErrors(bindingResult, measurementDTO);
        Optional<String> sensorName = Optional.of(measurementDTO.getSensor().getName());
        if (sensorName != null) {
            Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
            measurementService.save(measurement, sensorName.get());
            return ResponseEntity.ok(HttpStatus.OK);
        } else throw new MeasurementIssuesException("Sensor with name like this doesn't exist!");
    }

    public <T> void checkErrors(BindingResult bindingResult, T object) {
        if (object.getClass().equals(SensorDTO.class))
            sensorValidator.validate(object, bindingResult);
        else if (object.getClass().equals(MeasurementDTO.class))
            measurementValidator.validate(object, bindingResult);
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

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleRegistrationException(SQLException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleMeasurementException (MeasurementIssuesException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleSensorException (SensorWasNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
