package ua.vasylenko.SensorApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.vasylenko.SensorApp.dto.SensorDTO;
import ua.vasylenko.SensorApp.models.Sensor;
import ua.vasylenko.SensorApp.services.SensorService;
import ua.vasylenko.SensorApp.util.ErrorResponse;
import ua.vasylenko.SensorApp.util.ErrorsUtil;
import ua.vasylenko.SensorApp.util.SensorValidator;

@RestController
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final ErrorsUtil errorsUtil;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, ErrorsUtil errorsUtil) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.errorsUtil = errorsUtil;
    }

    @PostMapping("/sensor/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        errorsUtil.checkErrors(bindingResult,sensor);
        sensorService.registration(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleRegistrationException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
