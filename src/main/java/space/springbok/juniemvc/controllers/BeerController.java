package space.springbok.juniemvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.services.BeerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer saveNewBeer(@RequestBody Beer beer) {
        return beerService.saveNewBeer(beer);
    }

    @PutMapping("/{beerId}")
    public Beer updateBeerById(@PathVariable("beerId") Integer beerId, @RequestBody Beer beer) {
        return beerService.updateBeerById(beerId, beer).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeerById(@PathVariable("beerId") Integer beerId) {
        if (!beerService.deleteBeerById(beerId)) {
            throw new NotFoundException();
        }
    }
}
