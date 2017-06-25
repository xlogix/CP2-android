package xyz.fnplus.clientproject.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirebaseDataModel {
    private String loomNumber;
    private String lastReading;
    private String date;
    private String quality;
    private String shift;
    private String messSize;
    private String status;
    private String empCode;
    private String empName;
    private String dayOpenReading;
    private String dayEndReading;
    private String remark;
    private long time;

    public FirebaseDataModel() {
    }

    public FirebaseDataModel(String loomNumber, String lastReading, String date, String shift, String messSize, String status,
                             String empCode, String empName, String dayOpenReading, String dayEndReading, String remark, String quality) {
        this.loomNumber = loomNumber;
        this.lastReading = lastReading;
        this.date = date;
        this.shift = shift;
        this.messSize = messSize;
        this.status = status;
        this.empCode = empCode;
        this.empName = empName;
        this.dayOpenReading = dayOpenReading;
        this.dayEndReading = dayEndReading;
        this.remark = remark;
        this.quality = quality;

        // Initialize to current time
        time = new Date().getTime();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayEndReading() {
        return dayEndReading;
    }

    public void setDayEndReading(String dayEndReading) {
        this.dayEndReading = dayEndReading;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getMessSize() {
        return messSize;
    }

    public void setMessSize(String messSize) {
        this.messSize = messSize;
    }

    public String getLoomNumber() {
        return loomNumber;
    }

    public void setLoomNumber(String loomNumber) {
        this.loomNumber = loomNumber;
    }

    public String getLastReading() {
        return lastReading;
    }

    public void setLastReading(String lastReading) {
        this.lastReading = lastReading;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDayOpenReading() {
        return dayOpenReading;
    }

    public void setDayOpenReading(String dayOpenReading) {
        this.dayOpenReading = dayOpenReading;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDateFromStamp() {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

        return simpleDateFormat.format(date);
    }
}
