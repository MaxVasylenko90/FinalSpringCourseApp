package ua.vasylenko.SensorApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.vasylenko.SensorApp.dto.SensorDTO;
import ua.vasylenko.SensorApp.models.Sensor;
import ua.vasylenko.SensorApp.services.SensorService;
import ua.vasylenko.SensorApp.util.ErrorResponse;
import ua.vasylenko.SensorApp.util.SensorValidator;
import ua.vasylenko.SensorApp.util.SensorWasNotCreatedException;

@RestController
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/sensor/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        checkErrors(bindingResult, sensorDTO);
        sensorService.registration(modelMapper.map(sensorDTO, Sensor.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public void checkErrors(BindingResult bindingResult, SensorDTO object) {
        sensorValidator.validate(object, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors())
                sb.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            throw new SensorWasNotCreatedException(sb.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleRegistrationException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
