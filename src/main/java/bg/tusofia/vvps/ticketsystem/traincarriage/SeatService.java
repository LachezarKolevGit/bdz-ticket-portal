package java/bg/tusofia/vvps/ticketsystem/traincarriage;

@Service
public class SeatService(){

    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;

    }

    public void changeSeatState(Long seatId){
       Seat seat = seatRepository.findById(seatId);
        seat.setSeatState(SeatState.RESERVED);
        seatRepository.save(seat);

    }

}