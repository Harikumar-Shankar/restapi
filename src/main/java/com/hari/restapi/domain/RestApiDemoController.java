package com.hari.restapi.domain;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RestApiDemoController {
    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        this.coffees.addAll(List.of(
                new Coffee("Black"),
                new Coffee("Premium")));
    }

    @GetMapping("/coffees/{id}")
    public Optional<Coffee> getCoffeeById(@PathVariable final String id) {
        for (var coffee: coffees) {
            if (coffee.getId().equals(id)) {
                return Optional.of(coffee);
            }
        }
        return Optional.empty();
    }

    @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    public Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @PostMapping(value="/coffees")
    public Coffee addCoffee( @RequestBody final Coffee coffee) {
        coffees.add(coffee);
        return coffee;
    }

    @PutMapping("/coffees/{id}")
    public Coffee updateCoffee(@PathVariable String id, @RequestBody final Coffee coffee) {
        int coffeeIndex = -1;
        for (Coffee c: coffees) {
            if(c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return coffeeIndex != -1 ? coffee : addCoffee(coffee);
    }
}
