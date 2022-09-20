package org.gl.dto;
// Dto to get the Average marks status
public class AverageMarksDto {
    
    private String name;
    private int averageMarks;
    private String status;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAverageMarks() {
        return averageMarks;
    }
    public void setAverageMarks(int averageMarks) {
        this.averageMarks = averageMarks;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
