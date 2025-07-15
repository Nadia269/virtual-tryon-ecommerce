package com.shop.shop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.Model.ContactModel;

public interface ContactMessageRepository extends JpaRepository<ContactModel, Long> {

}
