package bg.tusofia.vvps.ticketsystem.traincarriage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainCarriageDTO {

    private TrainCarriageType trainCarriageType;
    private int totalSeats;
    private List<TrainCarriage> trainCarriageList;

}
