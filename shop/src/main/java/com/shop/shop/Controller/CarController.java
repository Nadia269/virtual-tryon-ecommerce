package com.shop.shop.Controller;

import com.shop.shop.Service.CarService;
import com.shop.shop.config.JwtService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.shop.Dtos.GetCarsOfUserDto;
import com.shop.shop.Dtos.GetCartLocationDto;
import com.shop.shop.Model.LocationsModel;

@RestController
@RequestMapping("/Car")
public class CarController {
    @Autowired
    private CarService carService;


    @PostMapping("/saveLocation")
    public ResponseEntity<Map<String, Object>> saveLocationToCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody GetCartLocationDto location) {
    
        Map<String, Object> response = new HashMap<>();
    
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("status", "error");
                response.put("message", "Authorization token is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
    
            if (email == null) {
                response.put("status", "error");
                response.put("message", "Invalid or expired token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            carService.saveLocation(email, location);
    
            response.put("status", "success");
            response.put("message", "Location saved to cart.");
            return ResponseEntity.ok(response);
    
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

    @GetMapping("/getLocations")
    public List<LocationsModel> getLocations(){
        return carService.getLocations();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCar(@RequestParam(name = "productId")Long productId, 
                                            @RequestParam(name = "quantity")int quantity,
                                            @RequestParam(name = "size")String size,
                                            @RequestHeader(value = "Authorization", required = false)String authHeader){
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing or malformed");
            }
    
            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
            
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
            }
    
            carService.addProductToCar(email, productId, quantity,size);
                     return ResponseEntity.ok("Product added successfully!");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing request: " + e.getMessage());
        }        
    }

    @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> editQuantity(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "quantity") int quantity,
            @RequestParam(name = "size")String size) {
        
        Map<String, Object> response = new HashMap<>();
    
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("status", "error");
                response.put("message", "Authorization token is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
    
            if (email == null) {
                response.put("status", "error");
                response.put("message", "Invalid or expired token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            carService.editQuantity(email, productId, quantity,size);
    
            response.put("status", "success");
            response.put("message", "Cart updated successfully");
            return ResponseEntity.ok(response);
    
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

    @PostMapping("/buy")
    public ResponseEntity<Map<String, Object>> buyCar(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("status", "error");
                response.put("message", "Authorization token is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);

            if (email == null) {
                response.put("status", "error");
                response.put("message", "Invalid or expired token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            boolean status = carService.buyCar(email);

            if (!status) {
                response.put("status", "info");
                response.put("message", "Cart already submitted.");
                return ResponseEntity.ok(response); // Or HttpStatus.CONFLICT if you prefer
            }

            response.put("status", "success");
            response.put("message", "Car bought successfully!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @GetMapping("/user")
    public ResponseEntity<?> getCartOfUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing or malformed");
            }

            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
            
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
            }

            GetCarsOfUserDto cart = carService.getAllCarsByUserEmail(email);
            
            return ResponseEntity.ok(cart);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing request: " + e.getMessage());
        }
    }   

    

    @DeleteMapping("/delete/product")
    public ResponseEntity<?> deleteProductFromCar(@RequestParam(name = "productId")Long productId,@RequestParam(name = "size")String size,@RequestHeader(value = "Authorization", required = false) String authHeader){
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Authorization token is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
            
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
            }
    
            carService.deleteProductFromCar(email, productId,size);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Product deleted successfully!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteCar(@RequestHeader(value = "Authorization", required = false) String authHeader){
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Authorization token is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
            
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
            }
    
            carService.deleteCar(email);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Product deleted successfully!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    
    }

    @GetMapping("/product")
    public ResponseEntity<?> countOfProductInCar(@RequestHeader(value = "Authorization", required = false) String authHeader){
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Authorization token is missing or malformed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
    
            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
            
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
            }
    
            int count = carService.countOfProducts(email);
            return ResponseEntity.ok(count);
            
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }  
    @GetMapping("/orders")
    public ResponseEntity<?> getCartOfUsers(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing or malformed");
            }

            String token = authHeader.substring(7);
            String email = JwtService.extractUsername(token);
            
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
            }

            List<GetCarsOfUserDto> cart = carService.getAllFinishedCartsByUserEmail(email);
            
            return ResponseEntity.ok(cart);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing request: " + e.getMessage());
        }
    }   
}
