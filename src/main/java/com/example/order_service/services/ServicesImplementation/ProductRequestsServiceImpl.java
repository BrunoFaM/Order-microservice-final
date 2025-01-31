package com.example.order_service.services.ServicesImplementation;

import com.example.order_service.dtos.NewOrderItem;
import com.example.order_service.exceptions.OrderErrorException;
import com.example.order_service.services.ProductRequestsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductRequestsServiceImpl implements ProductRequestsService {

    private static final Logger logger = LoggerFactory.getLogger(ProductRequestsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    @Value("${userservice.path}")
    private String urlUserService;
    @Value("${productservice.path}")
    private String urlProductService;

    @Override
    public void validateProductsStockAndExistence(List<NewOrderItem> mergedProducts) {
        try{
            restTemplate.postForEntity(urlProductService + "order/validation", mergedProducts, void.class);
        } catch (HttpStatusCodeException exception) {
            if(exception.getStatusCode().is4xxClientError()){
                throw new OrderErrorException(exception.getResponseBodyAsString());
            }else{
                logger.warn("Rest Template, something go wrong: ", exception);
                throw new RuntimeException(exception);
            }
        }

    }
}
