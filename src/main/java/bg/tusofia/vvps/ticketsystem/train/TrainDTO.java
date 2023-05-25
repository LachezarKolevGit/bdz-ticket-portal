package bg.tusofia.vvps.ticketsystem.train;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainDTO {
    private Integer[] trainCarriagesId;
    private LocalDateTime departingAt;
    private LocalDateTime arrivingAt;
    private Integer routeId;


}
