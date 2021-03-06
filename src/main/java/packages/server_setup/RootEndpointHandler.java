package packages.server_setup;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** This class handles all calls to the root endpoint. **/
@RestController
public class RootEndpointHandler {

    /**
     * A function that returns a message when users call this endpoint.
     * @return a response to the request that informs the users that this endpoint is not used by TraderAuto
     */
    @GetMapping("/")
    public String index() {
        return "This endpoint is not used by TraderAuto! Please make sure to specify an appropriate endpoint.";
    }

}