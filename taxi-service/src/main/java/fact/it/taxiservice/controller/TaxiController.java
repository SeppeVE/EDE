package fact.it.taxiservice.controller;

import fact.it.taxiservice.dto.TaxiRequest;
import fact.it.taxiservice.dto.TaxiResponse;
import fact.it.taxiservice.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxi")
@RequiredArgsConstructor
public class TaxiController {

    private final TaxiService taxiService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createTaxi
            (@RequestBody TaxiRequest taxiRequest){
        taxiService.createTaxi(taxiRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaxiResponse> getAllTaxisByBrand
            (@RequestBody String brand){
        return taxiService.getAllTaxisByBrand(brand);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TaxiResponse> getAllTaxis(){
        return taxiService.getAllTaxis();
    }
}