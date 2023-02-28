
package com.example.travelagency.controller;


import com.example.travelagency.controller.dto.user.AppUserDto;
import com.example.travelagency.model.AppUser;
import com.example.travelagency.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.travelagency.controller.mapper.AppUserDtoMapper.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserRestController {
    @Value("${app.url}")
    private String appUrl;
    private final AppUserService appUserService;

    @GetMapping("/get/{id}")
    public ResponseEntity<AppUserDto> getUser(@PathVariable Long id) {
        AppUser user = appUserService.getUser(id);
        return ResponseEntity.ok(mapAppUserToDto(user));
    }

    @GetMapping("/get")
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        return ResponseEntity.ok(mapAppUsersToDtos(users));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppUser> replaceAppUser(@PathVariable Long id, @RequestBody @Valid AppUserDto userDto) {
            appUserService.updateUser(mapDtoToAppUser(id, userDto));
            return ResponseEntity.noContent().build();
        }


    // add client -> niepotrzebne?

    // delete client
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AppUser> deleteUser(Long id) {
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }





    // get all clients in desc order by num of trips
    // add trip
    // remove future trip
    // get client with the most future trips
    // get client with the most past trips
    // get all clients that were in specified destination
    // get all clients that were in specified trip
    // client who didn't order trip in specified year
    // client who spent the most money

}



/*
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // add client
    @PostMapping
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
        Client savedClient = clientService.addClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // remove client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // update client
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    // delete client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // get all clients
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    // get all clients in desc order by spent money
    @GetMapping("/bySpentMoney")
    public ResponseEntity<List<Client>> getAllClientsBySpentMoney() {
        List<Client> clients = clientService.getAllClientsBySpentMoney();
        return ResponseEntity.ok(clients);
    }

    // get all clients in desc order by num of trips
    @GetMapping("/byNumOfTrips")
    public ResponseEntity<List<Client>> getAllClientsByNumOfTrips() {
        List<Client> clients = clientService.getAllClientsByNumOfTrips();
        return ResponseEntity.ok(clients);
    }

    // add trip
    @PostMapping("/{clientId}/trips/{tripId}")
    public ResponseEntity<Void> addTripToClient(@PathVariable Long clientId, @PathVariable Long tripId) {
        clientService.addTripToClient(clientId, tripId);
        return ResponseEntity.noContent().build();
    }

    // remove future trip
    @DeleteMapping("/{clientId}/trips/{tripId}")
    public ResponseEntity<Void> removeFutureTripFromClient(@PathVariable Long clientId, @PathVariable Long tripId) {
        clientService.removeFutureTripFromClient(clientId, tripId);
        return ResponseEntity.noContent().build();
    }

    // get client with the most future trips
    @GetMapping("/mostFutureTrips")
    public ResponseEntity<Client> getClientWithMostFutureTrips() {
        Client client = clientService.getClientWithMostFutureTrips();
        return ResponseEntity.ok(client);
    }

    // get client with the most past trips
    @GetMapping("/mostPastTrips")
    public ResponseEntity<Client> getClientWithMostPastTrips() {
        Client client = clientService.getClientWithMostPastTrips();
        return ResponseEntity.ok(client);
    }

    // get all clients that were in specified destination
    @GetMapping("/byDestination/{destinationId}")
    public ResponseEntity<List<Client>> getClientsByDestination(@PathVariable Long destinationId) {
        List<Client> clients = clientService.getClientsByDestination(destinationId);
        return ResponseEntity.ok(clients);
    }

    // get all clients that were in specified trip
    @GetMapping("/byTrip/{tripId}")
    public ResponseEntity<List<Client>> getClientsByTrip(@PathVariable Long tripId) {
        List<Client> clients = clientService.getClientsByTrip(tripId);
        return ResponseEntity.ok(clients);
    }

    // client who didn't order trip in specified year
    @GetMapping("/noTripIn/{year}")
    public ResponseEntity<List<Client>> getClientsWithNoTripsInYear(@PathVariable int year) {
        List<Client> clients = clientService.getClientsWithNoTripsInYear(year);
        return ResponseEntity.ok(clients);
    }

    // client who spent the most money
    @GetMapping("/highestSpender")
    public ResponseEntity<Client> getClientWithHighestSpending() {
        Client client = clientService.getClientWithHighestSpending();
        return ResponseEntity.ok(client);
    }
 */
