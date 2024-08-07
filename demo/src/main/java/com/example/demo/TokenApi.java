package com.example.CustomerMicroservices;

public class TokenApi {

    // Token Get endpoint
    //return anything without needing to check anything 
    // basically a hello world

    // Token Post endpoint ("/account/token")
    // Return token if customer login is valid return bad request otherwise
    // Parameters: Customer customer (customer wtih user and pw)
    public ResponseEntity<?> getToken(@RequestBody Customer customer) {

        // Get username and pw from customer

        // Make api call to CustomerAPI to get customer with same username

        // Compare passwords of customer from CustomerAPI & Customer from param

        // If same: return token in Response entity

        // if not same: resturn bad request response entity
    }
}
