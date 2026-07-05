package rba.card_creation;

import org.springframework.web.bind.annotation.*;
import rba.card_creation.model.Client;
import rba.card_creation.model.RequestResponse;
import rba.card_creation.model.NewCardRequest;

import java.util.List;

@RestController
@RequestMapping("api/v1/card-request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    public List<Client> getAllRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping()
    public RequestResponse newRequest(@RequestBody NewCardRequest request) throws Exception {
        return requestService.createNewRequest(request);
    }

    @GetMapping("/{oib}")
    public Client findRequestByOib(@PathVariable String oib) {
        return requestService.findRequestByOib(oib);
    }

    @DeleteMapping("/{oib}")
    public void deleteRequest(@PathVariable String oib) {
        requestService.deleteRequest(oib);
    }
}
