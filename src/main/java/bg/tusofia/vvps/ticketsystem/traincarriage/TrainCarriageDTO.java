package bg.tusofia.vvps.ticketsystem.traincarriage;

public class TrainCarriageDTO {

    private TrainCarriageType trainCarriageType;
    private int totalSeats;


    public TrainCarriageDTO() {
    }

    public TrainCarriageDTO(TrainCarriageType trainCarriageType, int totalSeats) {
        this.trainCarriageType = trainCarriageType;
        this.totalSeats = totalSeats;
    }

    public TrainCarriageType getTrainCarriageType() {
        return trainCarriageType;
    }

    public void setTrainCarriageType(TrainCarriageType trainCarriageType) {
        this.trainCarriageType = trainCarriageType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
