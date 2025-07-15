package com.shop.shop.Controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shop.Model.ContactModel;
import com.shop.shop.Service.ContactMessageService;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping("/Add")
    public ResponseEntity<String> submitContactMessage(@RequestBody ContactModel message) {
        contactMessageService.saveMessage(message);
        return ResponseEntity.ok("Message submitted successfully!");
    }
    @GetMapping("/Get")
    public List<ContactModel> getmessage(){
        return contactMessageService.getmessages();
    }
    @GetMapping("/del")
    public void delete(@RequestParam  (name = "id") long id){
     contactMessageService.delete(id);
    }


}
