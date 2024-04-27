package com.example.mainservice.services;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.ProductOrder;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.models.models.requests.ProductInOrderDtoReq;
import com.example.mainservice.repositories.OrderRepo;
import com.example.mainservice.repositories.ProductOrderRepo;
import com.example.mainservice.repositories.RegionRepo;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserService userService;
    private final RegionService regionService;
    private final ProductOrderRepo productOrderRepo;
    private final ProductService productService;

    public Order putProductsToOrder(User user, List<ProductInOrderDtoReq> req){
        Order order = orderRepo.findByUserAndOrderStatus(user, OrderStatus.DRAFT);
        order.getProductOrders().clear();
        for(ProductInOrderDtoReq product : req){
            ProductOrder pr = new ProductOrder();
            pr.setOrder(order);
            pr.setProduct(productService.findById(product.getProductId()));
            pr.setAmount(product.getAmount());
            order.getProductOrders().add(pr);
        }
        return orderRepo.save(order);
    }
    @Transactional
    public Order completeOrder(User userC, String region){
        User user = userService.findById(userC.getId());
        Order order = orderRepo.findByUserAndOrderStatus(user, OrderStatus.DRAFT);
        order.getProductOrders().forEach(o->{
            o.setTotalPrice(o.getAmount()*o.getProduct().getPrice());
        });
        order.setOrderTime(LocalDateTime.now());
        order.setRegion(regionService.findByName(region));
        orderRepo.save(order);

        Order no = new Order();
        order.setOrderStatus(OrderStatus.DRAFT);
        order.setUser(user);
        return orderRepo.save(no);
    }
}
