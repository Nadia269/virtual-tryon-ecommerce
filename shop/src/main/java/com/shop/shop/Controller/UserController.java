package com.shop.shop.Controller;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shop.Dtos.GetProductsDto;
import com.shop.shop.Dtos.UserDTO;
import com.shop.shop.Holder.TokenHolder;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Service.UserService;
import com.shop.shop.config.JwtService;
@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getallproducts")
    public List<GetProductsDto> getProducts(){
        return userService.getAllProducts();
    }
        @GetMapping("/getproduct")
    public ProductModel getProduct(@RequestParam(name = "id")Long id){
        return userService.getProduct(id);
    }

    @GetMapping("/getbycategory")
    public List<GetProductsDto> getbycategory(@RequestParam(name = "category")String category){
        return userService.getbycategory(category);
    }
    
    @GetMapping("/search")
    public ResponseEntity <GetProductsDto> search(@RequestParam(name = "pName")String pName){
        return userService.search(pName);
    }
    @PostMapping("/edituser")
    public ResponseEntity<UserModel> editUesr(@RequestBody UserModel user) {
        return userService.editUesr(user);
    }
    @PostMapping("/deleteuser")
    public void deleteUser(){
        userService.deleteUsr();
    }
    @PostMapping("/logout")
    public void logout() {
        userService.logout();
    }
    @GetMapping("/account")
    public ResponseEntity<UserDTO> getCurrentUser(){
        return userService.getCurrentUser();
    }
    @PostMapping("/editaccount")
    public ResponseEntity<UserModel> updateCurrentUser(@RequestBody UserModel user){
        return userService.updateCurrentUser(user, null);
    }
    @GetMapping("/id")
    public ResponseEntity<Map<String, Long>> getIdByUser(){
        return userService.getUserId();
        
    }

    

}