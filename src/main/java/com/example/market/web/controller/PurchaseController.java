package com.example.market.web.controller;

import com.example.market.domain.Purchase;
import com.example.market.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Purchase Controller", description = "This controller manages the operations of the purchases.")
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Operation(
            summary = "Get all supermarket products",
            description = "This endpoint retrieves a list of all products available in the supermarket. " +
                    "It returns detailed information about each product, including attributes such as name, price, and availability."
    )
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Search a product with an ID",
            description = "This endpoint retrieves a list of products associated with a specific client ID. " +
                    "It provides detailed information about each product, including attributes such as name, price, and availability. " +
                    "If the client ID is not found or has no associated products, the endpoint returns a 404 Not Found response."
    )
    @GetMapping("/client/{idClient}")
    public ResponseEntity<List<Purchase>> getByClient(@PathVariable("idClient") String clientId) {
        return purchaseService.getByClient(clientId).map(
                purchases -> new ResponseEntity<>(purchases, HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Save a new product to the supermarket",
            description = "This endpoint allows you to add a new product to the supermarket. " +
            "You need to provide the product details in the request body. " +
            "The endpoint returns the newly created product with a 201 Created status if the operation is successful."
    )
    @PostMapping("/save")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }
}
