package com.shop.shop.Dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.shop.shop.Model.ProductModel;

public class GetProductsDto {
    public Long id;
    public String category;
    public String image1;
    public String image2;
    public String image3;
    public String pDescription;
    public String pName;
    public BigDecimal price;
    public BigDecimal mainprice;
    public BigDecimal discount;
    public int quantitySold;
    public int stockQuantity;



    public GetProductsDto(Long id, String category, String image1, String image2, String image3, String p_description, String p_name, BigDecimal price, BigDecimal mainprice, BigDecimal discount, int quantity_sold, int stock_quantity) {
        this.id = id;
        this.category = category;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.pDescription = p_description;
        this.pName = p_name;
        this.price = price;
        this.mainprice = mainprice;
        this.discount = discount;
        this.quantitySold = quantity_sold;
        this.stockQuantity = stock_quantity;
    }

    public static List<GetProductsDto> convertProductsToDto(List<ProductModel> products){
        List<GetProductsDto> productsDto = new ArrayList<>();
            
            for (ProductModel product : products) {
                GetProductsDto productDto = new GetProductsDto(
                    product.getId(),
                    product.getCategory(),
                    product.getImage1(),
                    product.getImage2(),
                    product.getImage3(),
                    product.getpDescription(),
                    product.getpName(),
                    product.getPrice(),
                    product.getMainprice(),
                    product.getDiscount(),
                    product.getQuantitySold(),
                    product.getStockQuantity()
                );
                productsDto.add(productDto);
            }

            return  productsDto;
    }

    public static GetProductsDto convertProductToDto(ProductModel product){
        GetProductsDto productDto = new GetProductsDto(
                    product.getId(),
                    product.getCategory(),
                    product.getImage1(),
                    product.getImage2(),
                    product.getImage3(),
                    product.getpDescription(),
                    product.getpName(),
                    product.getPrice(),
                    product.getMainprice(),
                    product.getDiscount(),
                    product.getQuantitySold(),
                    product.getStockQuantity()
                );

            return  productDto;
    }
}

