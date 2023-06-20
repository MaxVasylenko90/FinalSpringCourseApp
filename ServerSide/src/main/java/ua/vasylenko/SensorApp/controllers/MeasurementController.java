package ua.vasylenko.SensorApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.vasylenko.SensorApp.dto.MeasurementDTO;
import ua.vasylenko.SensorApp.dto.MeasurementResponse;
import ua.vasylenko.SensorApp.models.Measurement;
import ua.vasylenko.SensorApp.services.MeasurementService;
import ua.vasylenko.SensorApp.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final ErrorsUtil errorsUtil;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, ErrorsUtil errorsUtil) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.errorsUtil = errorsUtil;
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        errorsUtil.checkErrors(bindingResult, measurement);
        Optional<String> sensorName = Optional.of(measurement.getSensor().getName());
        if (sensorName != null) {
            measurementService.save(measurement, sensorName.get());
            return ResponseEntity.ok(HttpStatus.OK);
        } else throw new SensorOrMeasurementIssuesException("Sensor with name like this doesn't exist!");
    }

    @GetMapping("/measurements")
    public MeasurementResponse allMeasurements() {
        return new MeasurementResponse(measurementService.findAll().stream()
                .map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @GetMapping("/measurements/rainyDaysCount")
    public int rainyDays() {
        return measurementService.rainyDaysCount().size();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleRegistrationException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
