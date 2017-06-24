package xyz.fnplus.clientproject.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirebaseDataModel {
    private String loomNumber;
    private String lastReading;
    private String date;
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
                             String empCode, String empName, String dayOpenReading, String dayEndReading, String remark) {
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

        // Initialize to current time
        time = new Date().getTime();
    }

    public String getDate() {
        return date;
    }

    public String getDayEndReading() {
        return dayEndReading;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessSize() {
        return messSize;
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
