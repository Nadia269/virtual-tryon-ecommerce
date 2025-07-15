package com.shop.shop.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shop.Dtos.GetCarsOfUserDto;
import com.shop.shop.Dtos.GetCartLocationDto;
import com.shop.shop.Dtos.GetProductsOfCarDto;
import com.shop.shop.Model.CarModel;
import com.shop.shop.Model.CarProductModel;
import com.shop.shop.Model.LocationsModel;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.CarProductRepository;
import com.shop.shop.Repository.CarRepository;
import com.shop.shop.Repository.LocationsRepository;
import com.shop.shop.Repository.ProductRepo;
import com.shop.shop.Repository.UserRepository;
import com.shop.shop.config.JwtService;

import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CarProductRepository carProductRepository;

    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private LocationsRepository locationsRepository;



    public List<LocationsModel> getLocations(){
        return locationsRepository.findAll();
    }


    
    @Transactional
    public CarModel createCarForUser(Long userId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        CarModel car = new CarModel();
        car.setUserModel(user);
        car.setCarDate(LocalDateTime.now());
        car.setTotalAmount(BigDecimal.ZERO);
        car.setGrandTotal(BigDecimal.ZERO);
        car.setShippingCost(BigDecimal.ZERO);
        
        return carRepository.save(car);
    }

    @Transactional
    public void saveLocation(String email, GetCartLocationDto location) {
        if (location == null) {
            throw new RuntimeException("Location not found");
        } 
        else {
            UserModel user = userRepository.findByEmail(email);
            CarModel cart = user.getCars().stream()
                .filter(c -> !c.isStatus())
                .findFirst()
                .orElse(null);

            if(cart == null){ 
                new RuntimeException("User not found with email: " + email);
            }
            cart.setCountry(location.country);
            cart.setState(location.state);
            cart.setZip(location.zip);
            cart.setAddress(location.address);

            carRepository.save(cart); 
        }
    }

    @Transactional
    public void addProductToCar(String email, Long productId, int quantity,String size) {
    
        UserModel user = userRepository.findByEmail(email);
        if(user == null){ 
            new RuntimeException("User not found with email: " + email);
        }

        ProductModel product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));


        CarModel car = user.getCars().stream()
                .filter(c -> !c.isStatus())
                .findFirst()
                .orElse(null);
        
        if (car == null) {
            car = new CarModel();
            car.setUserModel(user);
            car.setStatus(false);
            car.setTotalAmount(BigDecimal.ZERO);
            car.setGrandTotal(BigDecimal.ZERO);
            car.setCarDate(LocalDateTime.now());
            carRepository.save(car);
        }

        if (quantity > product.getStockQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getpName());
        }

        CarProductModel carProduct = carProductRepository.findByCarModelIdAndProductModelIdAndSize(car.getId(), productId, size);

        if (carProduct != null) {
            int newQuantity = carProduct.getQuantity() + quantity;
            if (newQuantity > product.getStockQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getpName());
            }
            carProduct.setQuantity(newQuantity);
        } else {
            carProduct = new CarProductModel();
            carProduct.setCarModel(car);
            carProduct.setProductModel(product);
            carProduct.setQuantity(quantity);
            carProduct.setSize(size);
        }

        carProductRepository.save(carProduct);
    
        if(carProduct == null){
            throw new RuntimeException("No products found in the cart for user with ID: " + user.getId());
        }


        BigDecimal mainPrice = product.getMainprice();
        if(product.getMainprice() == null){
            mainPrice = BigDecimal.ZERO;
        }
            
        BigDecimal totalAmount = car.getTotalAmount().add(mainPrice.multiply(BigDecimal.valueOf(quantity)));
        BigDecimal grandTotal = car.getGrandTotal().add(product.getPrice()).multiply(BigDecimal.valueOf(quantity));
        car.setTotalAmount(totalAmount);
        car.setGrandTotal(grandTotal);
        carRepository.save(car);

    }

    @Transactional
    public void editQuantity(String email, Long productId, int quantity,String size) {
        UserModel user = userRepository.findByEmail(email);

        if(user == null){ 
            new RuntimeException("User not found with email: " + email);
        }
            
        CarModel activeCar = user.getCars().stream()
                .filter(car -> !car.isStatus())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active car found for this user"));
                

        CarProductModel carProduct = carProductRepository.findByCarModelIdAndProductModelIdAndSize(activeCar.getId(), productId,size);
        if (carProduct == null) {
            throw new RuntimeException("Car does not contain the product with ID: " + productId);
        }

        ProductModel product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (quantity > product.getStockQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getpName());
        }

        int oldQuantity = carProduct.getQuantity();
        carProduct.setQuantity(quantity);
        carProductRepository.save(carProduct);

        BigDecimal totalAmount = activeCar.getTotalAmount();
        BigDecimal grandTotal = activeCar.getGrandTotal();
        BigDecimal price = product.getPrice();
        
        BigDecimal mainPrice = product.getMainprice();
        if(product.getMainprice() == null){
            mainPrice = BigDecimal.ZERO;
        }

        

        totalAmount = totalAmount.subtract(mainPrice.multiply(BigDecimal.valueOf(oldQuantity))); 
        totalAmount = totalAmount.add(mainPrice.multiply(BigDecimal.valueOf(quantity)));

        grandTotal = grandTotal.subtract(price.multiply(BigDecimal.valueOf(oldQuantity))); 
        grandTotal = grandTotal.add(price.multiply(BigDecimal.valueOf(quantity)));

        activeCar.setGrandTotal(grandTotal);
        activeCar.setTotalAmount(totalAmount);

        carRepository.save(activeCar);
    }



    @Transactional
    public boolean buyCar(String email) {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        CarModel activeCar = user.getCars().stream()
            .filter(car -> !car.isStatus())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No active car found for this user"));
        List<CarProductModel> carProducts = activeCar.getCarProducts();
    
        for (CarProductModel carProduct : carProducts) {
            ProductModel product = carProduct.getProductModel();
            int quantity = carProduct.getQuantity();
            int stockQuantity = product.getStockQuantity();
    
            if (stockQuantity < quantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getpName());
            }
        }
    
        for (CarProductModel carProduct : carProducts) {
            ProductModel product = carProduct.getProductModel();
            int quantity = carProduct.getQuantity();
            product.setStockQuantity(product.getStockQuantity() - quantity);
            product.setQuantitySold(product.getQuantitySold() + quantity);
            productRepo.save(product);
        }
    
        activeCar.setStatus(true);
        carRepository.save(activeCar);
    
        this.createCarForUser(user.getId());
    
        return true;
    }
    

    
    public GetCarsOfUserDto getAllCarsByUserEmail(String email){
        UserModel user = userRepository.findByEmail(email);

        if(user == null){ 
            new RuntimeException("User not found with email: " + email);
        }
    
        CarModel activeCar = user.getCars().stream()
                .filter(car -> !car.isStatus())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active car found for this user"));
                
        List<CarProductModel> carProduct = carProductRepository.findByCarModelId(activeCar.getId());
    
        if(carProduct == null){
            throw new RuntimeException("No products found in the cart for user with ID: " + user.getId());
        }
    
        List<GetProductsOfCarDto> productsOfCarList = GetProductsOfCarDto.convertProductsOfCarToDto(carProduct);
        return GetCarsOfUserDto.convertCarsToDto(activeCar, productsOfCarList);
    }
    
    
        
    @Transactional
    public void deleteProductFromCar(String email, Long productId,String size) {

        UserModel user = userRepository.findByEmail(email);

        CarModel activeCar = user.getCars().stream()
                .filter(car -> !car.isStatus())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active car found for this user"));

        CarProductModel carProduct = carProductRepository.findByCarModelIdAndProductModelIdAndSize(activeCar.getId(), productId,size);
        if (carProduct == null) {
            throw new RuntimeException("Product not found in cart");
        }

        ProductModel product = carProduct.getProductModel();

        BigDecimal productTotal = product.getMainprice().multiply(BigDecimal.valueOf(carProduct.getQuantity()));
        BigDecimal updatedTotal = activeCar.getTotalAmount().subtract(productTotal);

        BigDecimal productTotalAfterDescount = product.getPrice().multiply(BigDecimal.valueOf(carProduct.getQuantity()));
        BigDecimal updateGrandTotal = activeCar.getTotalAmount().subtract(productTotalAfterDescount);
        activeCar.setTotalAmount(updatedTotal);
        activeCar.setGrandTotal(updateGrandTotal);

        carProductRepository.delete(carProduct);
        carRepository.save(activeCar); 
    }


    @Transactional
    public void deleteCar(String email) {
        UserModel user = userRepository.findByEmail(email);

        if(user == null){ 
            new RuntimeException("User not found with email: " + email);
        }

        CarModel activeCar = user.getCars().stream()
            .filter(car -> !car.isStatus())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No active car found for this user"));
        List<CarProductModel> carProducts = activeCar.getCarProducts();

        if(activeCar == null){
            throw new RuntimeException("Car not found");
        }

        carRepository.delete(activeCar);
    }

    @Transactional
    public int countOfProducts(String email) {
        UserModel user = userRepository.findByEmail(email);

        if(user == null){ 
            new RuntimeException("User not found with email: " + email);
        }

        CarModel activeCar = user.getCars().stream()
            .filter(car -> !car.isStatus())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No active car found for this user"));
        List<CarProductModel> carProducts = activeCar.getCarProducts();

        if(activeCar == null){
            throw new RuntimeException("Car not found");
        }
        
        return carProducts.size();
    }

    public List<GetCarsOfUserDto> getAllFinishedCartsByUserEmail(String email) {
        UserModel user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        List<CarModel> finishedCarts = user.getCars().stream()
                .filter(CarModel::isStatus) // status == true
                .collect(Collectors.toList());

        if (finishedCarts.isEmpty()) {
            throw new RuntimeException("No finished carts found for this user");
        }

        List<GetCarsOfUserDto> finishedCartsDtos = new ArrayList<>();

        for (CarModel cart : finishedCarts) {
            List<CarProductModel> carProducts = carProductRepository.findByCarModelId(cart.getId());

            if (carProducts == null || carProducts.isEmpty()) {
                continue; // Skip empty carts
            }

            List<GetProductsOfCarDto> productDtos = GetProductsOfCarDto.convertProductsOfCarToDto(carProducts);
            GetCarsOfUserDto cartDto = GetCarsOfUserDto.convertCarsToDto(cart, productDtos);
            finishedCartsDtos.add(cartDto);
        }

        if (finishedCartsDtos.isEmpty()) {
            throw new RuntimeException("No finished carts with products found for this user");
        }

        return finishedCartsDtos;
    }



}
