package com.shop.shop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shop.Model.LocationsModel;

public interface LocationsRepository extends JpaRepository<LocationsModel, Long>{
    

}
