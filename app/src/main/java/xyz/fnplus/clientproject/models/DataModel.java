package xyz.fnplus.clientproject.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DataModel {
    private String loomNumber;
    private String lastReading;
    private String date;
    private String quality;
    private String shift;
    private String messSize;
    private String status;
    private HashMap<String, EmpRecord> emplist;

    private long time;

    public DataModel() {
    }

    public DataModel(String loomNumber, String lastReading, String date, String shift, String messSize, String status,
                     HashMap<String, EmpRecord> emplist, String quality) {
        this.loomNumber = loomNumber;
        this.lastReading = lastReading;
        this.date = date;
        this.shift = shift;
        this.messSize = messSize;
        this.status = status;
        this.emplist = emplist;

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

    public HashMap<String, EmpRecord> getEmplist() {
        return emplist;
    }

    public void setEmplist(HashMap<String, EmpRecord> emplist) {
        this.emplist = emplist;
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

    public static class EmpRecord {
        String empCode;
        String empName;
        String openReading;
        String closeReading;
        String remarks;

        public EmpRecord() {
        }

        public EmpRecord(String empCode, String empName, String openReading, String closeReading, String remarks) {
            this.empCode = empCode;
            this.empName = empName;
            this.openReading = openReading;
            this.closeReading = closeReading;
            this.remarks = remarks;
        }

        @Override
        public String toString() {
            return String.format("%-10s %30s- Open Reading:%s\n Close Reading:%s", empCode, empName, openReading, closeReading);
        }


        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getOpenReading() {
            return openReading;
        }

        public void setOpenReading(String openReading) {
            this.openReading = openReading;
        }

        public String getCloseReading() {
            return closeReading;
        }

        public void setCloseReading(String closeReading) {
            this.closeReading = closeReading;
        }
    }
}
