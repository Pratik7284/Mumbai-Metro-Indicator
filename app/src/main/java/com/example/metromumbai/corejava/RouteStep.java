package com.example.metromumbai.corejava;

/**
 * Detailed step in a computed metro route.
 */
public class RouteStep {
    public enum StepType {
        BOARD,
        INTERMEDIATE,
        INTERCHANGE_TRANSFER,
        ALIGHT
    }

    private final MetroStation station;
    private final StepType stepType;
    private final String lineId;
    private final String instruction;
    private final double timeFromStartMinutes;
    private final String platformNote;

    public RouteStep(MetroStation station, StepType stepType, String lineId,
                     String instruction, double timeFromStartMinutes, String platformNote) {
        this.station = station;
        this.stepType = stepType;
        this.lineId = lineId;
        this.instruction = instruction;
        this.timeFromStartMinutes = timeFromStartMinutes;
        this.platformNote = platformNote != null ? platformNote : "";
    }

    public MetroStation getStation() {
        return station;
    }

    public StepType getStepType() {
        return stepType;
    }

    public String getLineId() {
        return lineId;
    }

    public String getInstruction() {
        return instruction;
    }

    public double getTimeFromStartMinutes() {
        return timeFromStartMinutes;
    }

    public String getPlatformNote() {
        return platformNote;
    }
}
