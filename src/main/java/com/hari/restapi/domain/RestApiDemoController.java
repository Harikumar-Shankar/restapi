package com.hari.restapi.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Elevated common URI path on all the endpoints to the class level
@RequestMapping("/coffees")
@RestController
public class RestApiDemoController {
    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        this.coffees.addAll(List.of(
                new Coffee("Black"),
                new Coffee("Premium")));
    }

    // @GetMapping("/coffees/{id}")
    @GetMapping("/{id}")
    public Optional<Coffee> getCoffeeById(@PathVariable final String id) {
        for (var coffee : coffees) {
            if (coffee.getId().equals(id)) {
                return Optional.of(coffee);
            }
        }
        return Optional.empty();
    }

    // @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    @GetMapping
    public Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @PostMapping
    // @PostMapping(value = "/coffees")
    public Coffee addCoffee(@RequestBody final Coffee coffee) {
        coffees.add(coffee);
        return coffee;
    }

    // @PutMapping("/coffees/{id}")
    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable String id, @RequestBody final Coffee coffee) {
        int coffeeIndex = -1;
        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        // Based on IETF, GET method does not need status code, Post needs suggestion and PUT requires proper code
        // We are returning 201(CREATED) if there is create and 200(OK) for updates to the existing.
        // return coffeeIndex != -1 ? coffee : addCoffee(coffee);
        return coffeeIndex == -1 ? new ResponseEntity<>(addCoffee(coffee), HttpStatus.CREATED) : new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    // @DeleteMapping("/coffees/{id}")
    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable final String id) {
        coffees.removeIf(coffee -> coffee.getId().equals(id));
    }
}
