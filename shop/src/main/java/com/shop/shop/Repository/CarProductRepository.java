package com.shop.shop.Repository;
import java.util.List;

import com.shop.shop.Model.CarProductModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarProductRepository extends JpaRepository<CarProductModel, Long> {
    CarProductModel findByCarModelIdAndProductModelIdAndSize(Long carModelId, Long productModelId, String size);
    List<CarProductModel> findByCarModelId(Long carId);
    
}
