package com.example.easymechproject;

public class Note_Collector {

    String Appointment_Date;
    String Appointment_Time;
    String Extra_Services;
    String Required_Service;
    String Total_Cost;
    String Status;

    public Note_Collector() {
    }

    public Note_Collector(String Appointment_Date, String Appointment_Time, String Extra_Services, String Required_Service, String Total_Cost, String Status) {
        this.Appointment_Date = Appointment_Date;
        this.Appointment_Time = Appointment_Time;
        this.Extra_Services = Extra_Services;
        this.Required_Service = Required_Service;
        this.Total_Cost = Total_Cost;
        this.Status = Status;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAppointment_Date() {
        return Appointment_Date;
    }

    public void setAppointment_Date(String Appointment_Date) {
        this.Appointment_Date = Appointment_Date;
    }

    public String getAppointment_Time() {
        return Appointment_Time;
    }

    public void setAppointment_Time(String Appointment_Time) {
        this.Appointment_Time = Appointment_Time;
    }

    public String getExtra_Services() {
        return Extra_Services;
    }

    public void setExtra_Services(String Extra_Services) {
        this.Extra_Services = Extra_Services;
    }

    public String getRequired_Service() {
        return Required_Service;
    }

    public void setRequired_Service(String Required_Service) {
        this.Required_Service = Required_Service;
    }

    public String getTotal_Cost() {
        return Total_Cost;
    }

    public void setTotal_Cost(String Total_Cost) {
        this.Total_Cost = Total_Cost;
    }
}


