package com.milton.propertychatbotbackend.scheduler;

import com.milton.propertychatbotbackend.models.Appointment;
import com.milton.propertychatbotbackend.service.AppointmentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ReminderJob implements Job {
    private final AppointmentService appointmentService;

    public ReminderJob(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        List<Appointment> upcoming = appointmentService.getAppointmentsInNextHour();
        for (Appointment apt : upcoming) {
            System.out.println("[Reminder] Upcoming appointment at " + apt.getAppointmentTime() + " for " + apt.getTenantName());
        }
    }
}