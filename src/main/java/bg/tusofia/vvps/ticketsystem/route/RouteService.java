package bg/tusofia/vvps/ticketsystem/route;

@Service
public class RouteService{

    private RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    public void calculateDistance(Long id){
       Route route = routeRepository.getById(id);
       
      List<TrainStation> stops = route.getStops();  //change set implementation to list because set doesnt support getIndex
      TrainStation firstStation = stops.get(0);
      TrainStation firstStation = stops.get(stops.size() - 1);
      

    }


}