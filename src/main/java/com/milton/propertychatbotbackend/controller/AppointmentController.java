package com.milton.propertychatbotbackend.controller;

import com.milton.propertychatbotbackend.dto.AppointmentDto;
import com.milton.propertychatbotbackend.models.Appointment;
import com.milton.propertychatbotbackend.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController(value = "/api/appointment")
public class AppointmentController {

     private final AppointmentService appointmentService;

     public AppointmentController(AppointmentService appointmentService) {
         this.appointmentService = appointmentService;
     }

     @PostMapping
     public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDto appointmentDto) {
         Appointment appointment = new Appointment();
         appointment.setPropertyAddress(appointmentDto.getPropertyAddress());
         appointment.setTenantName(appointmentDto.getTenantName());
         appointment.setLandlordName(appointmentDto.getLandlordName());
         appointment.setAppointmentTime(appointmentDto.getAppointmentTime());
         appointment.setLandlordContact(appointmentDto.getLandlordContact());
         appointment.setTenantContact(appointmentDto.getTenantContact());

         Appointment createdAppointment = appointmentService.createAppointment(appointment);
         return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
     }




    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(id));
        return appointment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto) {
        Optional<Appointment> updatedAppointment = Optional.ofNullable(appointmentService.updateAppointment(id, appointmentDto));
        return updatedAppointment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        boolean isDeleted = appointmentService.deleteAppointment(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
