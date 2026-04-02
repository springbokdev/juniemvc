package space.springbok.juniemvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.services.BeerService;

import java.util.List;

/**
 * Controller for managing Beer operations.
 */
@RestController
@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    /**
     * List all beers.
     *
     * @return a list of all beers.
     */
    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    /**
     * Get a beer by its ID.
     *
     * @param beerId the ID of the beer to retrieve.
     * @return the beer if found.
     * @throws NotFoundException if the beer is not found.
     */
    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    /**
     * Save a new beer.
     *
     * @param beer the beer object to save.
     * @return the saved beer.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer saveNewBeer(@RequestBody Beer beer) {
        return beerService.saveNewBeer(beer);
    }

    /**
     * Update an existing beer by its ID.
     *
     * @param beerId the ID of the beer to update.
     * @param beer   the updated beer object.
     * @return the updated beer.
     * @throws NotFoundException if the beer to update is not found.
     */
    @PutMapping("/{beerId}")
    public Beer updateBeerById(@PathVariable("beerId") Integer beerId, @RequestBody Beer beer) {
        return beerService.updateBeerById(beerId, beer).orElseThrow(NotFoundException::new);
    }

    /**
     * Delete a beer by its ID.
     *
     * @param beerId the ID of the beer to delete.
     * @throws NotFoundException if the beer to delete is not found.
     */
    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeerById(@PathVariable("beerId") Integer beerId) {
        if (!beerService.deleteBeerById(beerId)) {
            throw new NotFoundException();
        }
    }
}
