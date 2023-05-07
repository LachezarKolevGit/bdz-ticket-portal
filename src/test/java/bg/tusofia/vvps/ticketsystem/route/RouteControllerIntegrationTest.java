package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.security.WebSecurityConfigurer;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Import(WebSecurityConfigurer.class)
@WebMvcTest(RouteController.class)
public class RouteControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteRepository routeRepository;

    @MockBean
    private RouteService routeService;

    @Test
    void testUnauthenticatedUserAccessingRoutesById() throws Exception {
        this.mockMvc
                .perform(get("/routes/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUnauthenticatedUserAccessingRoutes() throws Exception {
        this.mockMvc
                .perform(get("/routes"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUnauthenticatedUserAccessingRoutesWithPOST() throws Exception {
        this.mockMvc
                .perform(post("/routes"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testControllerReturnRouteByPathVariable() throws Exception {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route expectedRoute = new Route(trainStationList, null);

        when(routeRepository.findById(1L)).thenReturn(Optional.of(expectedRoute));
        when(routeService.getRoute(1L)).thenReturn(expectedRoute);

        this.mockMvc.perform(get("/route/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("route/route_train_stations"))
                .andExpect(model().attributeExists("route"));

    }


}
