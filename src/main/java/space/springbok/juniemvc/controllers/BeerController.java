package space.springbok.juniemvc.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.springbok.juniemvc.exceptions.NotFoundException;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.models.BeerPatchDto;
import space.springbok.juniemvc.services.BeerService;

/**
 * Controller for managing Beer operations.
 */
@RestController
@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
class BeerController {

    private final BeerService beerService;

    /**
     * List all beers.
     *
     * @param beerName  the name of the beer to filter by (optional).
     * @param beerStyle the style of the beer to filter by (optional).
     * @param page      the page number to retrieve (optional).
     * @param size      the number of beers per page (optional).
     * @return a page of beers as BeerDto.
     */
    @GetMapping
    Page<BeerDto> listBeers(@RequestParam(value = "beerName", required = false) String beerName,
                            @RequestParam(value = "beerStyle", required = false) String beerStyle,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "size", required = false) Integer size) {
        return beerService.listBeers(beerName, beerStyle, page, size);
    }

    /**
     * Get a beer by its ID.
     *
     * @param beerId the ID of the beer to retrieve.
     * @return the beer as BeerDto if found.
     * @throws NotFoundException if the beer is not found.
     */
    @GetMapping("/{beerId}")
    BeerDto getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    /**
     * Save a new beer.
     *
     * @param beer the BeerDto object to save.
     * @return the saved BeerDto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BeerDto saveNewBeer(@Valid @RequestBody BeerDto beer) {
        return beerService.saveNewBeer(beer);
    }

    /**
     * Update an existing beer by its ID.
     *
     * @param beerId the ID of the beer to update.
     * @param beer   the updated BeerDto object.
     * @return the updated BeerDto.
     * @throws NotFoundException if the beer to update is not found.
     */
    @PutMapping("/{beerId}")
    BeerDto updateBeerById(@PathVariable("beerId") Integer beerId, @Valid @RequestBody BeerDto beer) {
        return beerService.updateBeerById(beerId, beer).orElseThrow(NotFoundException::new);
    }

    /**
     * Patch an existing beer by its ID.
     *
     * @param beerId the ID of the beer to patch.
     * @param beer   the BeerPatchDto object with updated fields.
     * @return the updated BeerDto.
     * @throws NotFoundException if the beer to patch is not found.
     */
    @PatchMapping("/{beerId}")
    BeerDto patchBeerById(@PathVariable("beerId") Integer beerId, @RequestBody BeerPatchDto beer) {
        return beerService.patchBeerById(beerId, beer).orElseThrow(NotFoundException::new);
    }

    /**
     * Delete a beer by its ID.
     *
     * @param beerId the ID of the beer to delete.
     * @throws NotFoundException if the beer to delete is not found.
     */
    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBeerById(@PathVariable("beerId") Integer beerId) {
        if (!beerService.deleteBeerById(beerId)) {
            throw new NotFoundException();
        }
    }
}
