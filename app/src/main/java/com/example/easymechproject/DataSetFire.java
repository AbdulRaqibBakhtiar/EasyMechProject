package com.example.easymechproject;

public class DataSetFire {

    String Appointment_Date;
    String Appointment_Time;
    String Driver_Name;
    String Driver_Phone;
    String Driver_Email;
    String Extra_Services;
    String Required_Service;
    String Total_Cost;
    String Car_Plate;
    String Car_Model;
    String Driver_Address;
    String Status;

    public DataSetFire() {
    }

    public DataSetFire(String appointment_Date, String appointment_Time, String driver_Name, String driver_Phone,
                       String driver_Email, String extra_Services, String required_Service, String total_Cost,
                       String car_Plate, String car_model, String driver_Address, String status) {
        Appointment_Date = appointment_Date;
        Appointment_Time = appointment_Time;
        Driver_Name = driver_Name;
        Driver_Phone = driver_Phone;
        Driver_Email = driver_Email;
        Extra_Services = extra_Services;
        Required_Service = required_Service;
        Total_Cost = total_Cost;
        Car_Plate = car_Plate;
        Car_Model = car_model;
        Driver_Address = driver_Address;
        Status = status;
    }

    public String getDriver_Email() {
        return Driver_Email;
    }

    public void setDriver_Email(String driver_Email) {
        Driver_Email = driver_Email;
    }

    public String getCar_Model() {
        return Car_Model;
    }

    public void setCar_Model(String car_model) {
        Car_Model = car_model;
    }

    public String getDriver_Address() {
        return Driver_Address;
    }

    public void setDriver_Address(String driver_Address) {
        Driver_Address = driver_Address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCar_Plate() {
        return Car_Plate;
    }

    public void setCar_Plate(String car_Plate) {
        Car_Plate = car_Plate;
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

    public String getDriver_Name() {
        return Driver_Name;
    }

    public void setDriver_Name(String Driver_Name) {
        this.Driver_Name = Driver_Name;
    }

    public String getDriver_Phone() {
        return Driver_Phone;
    }

    public void setDriver_Phone(String Driver_Phone) {
        this.Driver_Phone = Driver_Phone;
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


