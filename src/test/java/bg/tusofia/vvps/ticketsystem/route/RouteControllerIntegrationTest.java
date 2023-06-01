package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class RouteControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testUnauthenticatedUserAccessingRoutesById() throws Exception {
        this.mockMvc
                .perform(get("/routes/1")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUnauthenticatedUserAccessingRoutes() throws Exception {
        this.mockMvc
                .perform(get("/routes")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUnauthenticatedUserAccessingRoutesWithPOST() throws Exception {
        this.mockMvc
                .perform(post("/routes")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testControllerReturnRouteByPathVariable() throws Exception {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route expectedRoute = new Route(trainStationList, null);
        routeRepository.save(expectedRoute);

        this.mockMvc.perform(get("/route/" + expectedRoute.getId())
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("route/route_train_stations"))
                .andExpect(model().attributeExists("route"));
    }

    @Test
    void testControllerGetRequestToRoutesWithClientRole() throws Exception {
        this.mockMvc.perform(get("/routes")
                        .with(user("client").roles("CLIENT"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void testControllerPostRequestToRoutesWithUserRole() throws Exception {
        this.mockMvc.perform(post("/route")
                        .with(user("client").roles("CLIENT"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Disabled
    @Test
    void testControllerPostRequestToRoutesWithAdministratorRole() throws Exception {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        RouteDTO routeDTO = new RouteDTO(trainStationList, null);

        this.mockMvc.perform(post("/route")
                        .param("trainStations", String.valueOf(trainStationList))
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("route/route_created_successfully"))
                .andExpect(model().attributeExists("newRouteId"));

        Route route = routeRepository.findById(Long.valueOf(model().toString()))
                .orElseThrow(() -> new EntityNotFoundException());
        assertEquals(routeDTO.trainStations(), route.getTrainStations(),
                "The trainStations on the saved route doesn't match the trainStations on the expected route");
        assertEquals(routeDTO.trains(), route.getTrains(), "Trains in");
    }


}
