package lab12;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/consume")
public class ConsumerController {

    @Inject
    private ConsumerService consumerService;

    @GET
    @Path("/start")
    public String startConsuming() {
        consumerService.startConsumer();
        return "Consumer Started!";
    }
}