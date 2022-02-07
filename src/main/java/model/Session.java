package model;

import java.util.List;

public class Session {
    private long startTime;
    private long endTime;
    private int deliveries;
    private int paidTime;
    private int unpaidTime;
    private double paidDistance;
    private double totalDistance;

    public void setPaidDistance(double paidDistance) {
        this.paidDistance = paidDistance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    private List<Stop> stops;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(int deliveries) {
        this.deliveries = deliveries;
    }

    public int getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(int paidTime) {
        this.paidTime = paidTime;
    }

    public int getUnpaidTime() {
        return unpaidTime;
    }

    public void setUnpaidTime(int unpaidTime) {
        this.unpaidTime = unpaidTime;
    }

    public double getPaidDistance() {
        return paidDistance;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }


}

