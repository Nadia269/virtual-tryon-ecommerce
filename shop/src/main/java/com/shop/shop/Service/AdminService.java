package com.shop.shop.Service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Repository.ProductRepo;


@Service
public class AdminService {

    @Autowired 
    private ProductRepo productsRepo;

    public ProductModel AddProduct( ProductModel product) {
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal originalprice = product.getMainprice();
        BigDecimal discount = product.getDiscount();
        BigDecimal amountOfDiscount =originalprice.multiply(discount).divide(hundred);
        product.setPrice(product.getMainprice().subtract(amountOfDiscount));
        product.setQuantitySold(0);
        productsRepo.save(product);
        return product; 
    }

    public ResponseEntity <ProductModel> EditProduct(ProductModel productold) {
                try {
                ProductModel productnew =productsRepo.findById(productold.getId()).get();
                productnew.setpName(productold.getpName());
                productnew.setpDescription(productold.getpDescription());
                productnew.setCategory(productold.getCategory());
                productnew.setImage1(productold.getImage1());
                productnew.setImage2(productold.getImage2());
                productnew.setImage3(productold.getImage3());
                productnew.setMainprice(productold.getMainprice());
                productnew.setDiscount(productold.getDiscount());
                BigDecimal hundred = new BigDecimal(100);
                BigDecimal originalprice = productold.getMainprice();
                BigDecimal discount = productold.getDiscount();
                BigDecimal amountOfDiscount =originalprice.multiply(discount).divide(hundred);
                productnew.setPrice(productold.getMainprice().subtract(amountOfDiscount));
                productnew.setQuantitySold(productold.getQuantitySold());
                productnew.setStockQuantity(productold.getStockQuantity());
                productsRepo.save(productnew);
                return new ResponseEntity<>(productnew,HttpStatus.OK); 

            } catch (Exception e) {
                System.out.println("this product not can edit (not found)");
                return new ResponseEntity<>(productold,HttpStatus.NOT_FOUND); 
            }
    }

    public ResponseEntity<Void> delProduct(Long id) {
        try {
            productsRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("this product not can delete (not found)");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
    }
}
