package com.example.order_service.services;

import com.example.order_service.dtos.NewOrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductRequestsService {

    void validateProductsStockAndExistence(List<NewOrderItem> products);


}
