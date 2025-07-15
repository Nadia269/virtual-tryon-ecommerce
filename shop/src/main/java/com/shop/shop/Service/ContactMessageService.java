package com.shop.shop.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shop.Model.ContactModel;
import com.shop.shop.Repository.ContactMessageRepository;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public ContactModel saveMessage(ContactModel message) {
        ContactModel user = new ContactModel();
        user.setName(message.getName());
        user.setEmail(message.getEmail());
        user.setMsg(message.getMsg());
        user.setSubject(message.getSubject());

        return contactMessageRepository.save(user);
    }
    public List<ContactModel> getmessages(){
      List<ContactModel> messages = contactMessageRepository.findAll();
      return messages;
    }
    public void delete(Long id){
      contactMessageRepository.deleteById(id);
    }
}