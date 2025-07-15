package com.shop.shop.Service;


import com.shop.shop.Dtos.GetProductsDto;
import com.shop.shop.Dtos.UserDTO;
import com.shop.shop.Holder.TokenHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.ProductRepo;
import com.shop.shop.Repository.UserRepository;
import com.shop.shop.config.JwtService;

import java.util.Objects;




@Service
public class UserService {
    @Autowired
    private  ProductRepo productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // list of all products
       public  List <GetProductsDto>  getAllProducts(){
            List<ProductModel> products = productRepository.findAll();
            return GetProductsDto.convertProductsToDto(products);
    }
    // get product by id
    public  ProductModel  getProduct(Long id){
        return productRepository.findById(id).get() ;
     }

    // list of products in the same category
    public  List<GetProductsDto> getbycategory(String category) {
            List<ProductModel> products = productRepository.findByCategory(category);
            return GetProductsDto.convertProductsToDto(products);
    }

    // search for product by name
    public  ResponseEntity <GetProductsDto> search(String pName) {
        try {
            ProductModel product = productRepository.findBypName(pName);
            GetProductsDto productDto = GetProductsDto.convertProductToDto(product);
            return  new ResponseEntity<>( productDto ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    //edit should send password and send role with data 
    public ResponseEntity<UserModel> editUesr(UserModel user) {
        try {
            
            UserModel updateUser =userRepository.findByEmail(JwtService.extractUsername(TokenHolder.getToken()));
            updateUser.setFullname(user.getFullname());
            updateUser.setName(user.getName());
            updateUser.setAddress(user.getAddress());
            updateUser.setEmail(user.getEmail());
            updateUser.setPhone(user.getPhone());
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updateUser.setPicture(user.getPicture());
            updateUser.setRole(user.getRole());
            userRepository.save(updateUser);
            return new ResponseEntity<>(updateUser,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
        }

    }
    //deleteuser

    public ResponseEntity<Void> deleteUsr() {
        try {
            UserModel user=userRepository.findByEmail(JwtService.extractUsername(TokenHolder.getToken()));
            userRepository.delete(user);
            TokenHolder.clearToken();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("the user cant delete (not found)");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
    }

    public void logout() {
        TokenHolder.clearToken();
    }

    public ResponseEntity<UserDTO> getCurrentUser() {
    String email = JwtService.extractUsername(TokenHolder.getToken());
    UserModel currentUser = userRepository.findByEmail(email);

    if (currentUser == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    UserDTO dto = new UserDTO(currentUser);
    return new ResponseEntity<>(dto, HttpStatus.OK);
}

  public ResponseEntity<UserModel> updateCurrentUser(
        @ModelAttribute UserModel user,
        @RequestPart(value = "picture", required = false) MultipartFile pictureFile
) {
    try {
        String email = JwtService.extractUsername(TokenHolder.getToken());
        UserModel currentUser = userRepository.findByEmail(email);

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isUpdated = false;

        // Check for changes
        if (!currentUser.getFullname().equals(user.getFullname())) {
            currentUser.setFullname(user.getFullname());
            isUpdated = true;
        }
        if (!currentUser.getName().equals(user.getName())) {
            currentUser.setName(user.getName());
            isUpdated = true;
        }
        if (!currentUser.getAddress().equals(user.getAddress())) {
            currentUser.setAddress(user.getAddress());
            isUpdated = true;
        }
        if (!currentUser.getPhone().equals(user.getPhone())) {
            currentUser.setPhone(user.getPhone());
            isUpdated = true;
        }
  if (!Objects.equals(currentUser.getPicture(), user.getPicture())) {
    currentUser.setPicture("images/"+user.getPicture());
    isUpdated = true;
}

        else{
          System.out.println("hello");
          currentUser.setPicture(user.getPicture());  
        }

        // If no changes were made, return the current user without saving
        if (!isUpdated) {
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }

        userRepository.save(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);

    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}



    public ResponseEntity<Map<String, Long>> getUserId() {
        String email = JwtService.extractUsername(TokenHolder.getToken());
        UserModel currentUser = userRepository.findByEmail(email);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Long> response = new HashMap<>();
        response.put("id", currentUser.getId());
        return ResponseEntity.ok(response);
    }

}


   




