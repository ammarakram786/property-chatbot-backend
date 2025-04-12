package com.milton.propertychatbotbackend.service;

import com.milton.propertychatbotbackend.dto.AppointmentDto;
import com.milton.propertychatbotbackend.models.Appointment;
import com.milton.propertychatbotbackend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsInNextHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1);
        return appointmentRepository.findByAppointmentTimeBetween(now, nextHour);
    }


    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment updateAppointment(Long id, AppointmentDto appointment) {
        if (appointmentRepository.existsById(id)) {
            Appointment existingAppointment = appointmentRepository.findById(id).orElse(null);
            assert existingAppointment != null;
            existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
            existingAppointment.setPropertyAddress(appointment.getPropertyAddress());
            existingAppointment.setTenantName(appointment.getTenantName());
            existingAppointment.setLandlordName(appointment.getLandlordName());
            existingAppointment.setLandlordContact(appointment.getLandlordContact());
            existingAppointment.setTenantContact(appointment.getTenantContact());

            return appointmentRepository.save(existingAppointment);
        }
        return null;
    }

    public boolean deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
        return false;
    }
}
