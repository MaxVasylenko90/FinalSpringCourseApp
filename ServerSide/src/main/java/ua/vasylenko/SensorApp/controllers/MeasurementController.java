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
import ua.vasylenko.SensorApp.models.Measurement;
import ua.vasylenko.SensorApp.services.MeasurementService;
import ua.vasylenko.SensorApp.util.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
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

    @GetMapping("/measurements")
    public List<MeasurementDTO> allMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/measurements/rainyDaysCount")
    public int rainyDays() {
        return measurementService.rainyDaysCount().size();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    public void checkErrors(BindingResult bindingResult, MeasurementDTO object) {
        measurementValidator.validate(object, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors())
                sb.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            throw new MeasurementIssuesException(sb.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleRegistrationException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
