package com.example.mainservice.services;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.ProductOrder;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.models.models.requests.ProductInOrderDtoReq;
import com.example.mainservice.models.models.responses.AchievementDtoRes;
import com.example.mainservice.repositories.OrderRepo;
import com.example.mainservice.repositories.ProductOrderRepo;
import com.example.mainservice.repositories.RegionRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        no.setOrderStatus(OrderStatus.DRAFT);
        no.setUser(user);
        return orderRepo.save(no);
    }
    @Transactional
    public Page<Order> getSelfOrderList(User user, Pageable pageable){
        Page<Order> res = orderRepo.findAllByUserAndOrderStatus(user, OrderStatus.COMPLETED, pageable);
        return res;
    }
    private final EntityManager entityManager;
    public AchievementDtoRes calculateRansomAmount(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Order> orderRoot = query.from(Order.class);
        Join<Order, ProductOrder> productOrderJoin = orderRoot.join("productOrders");

        query.select(criteriaBuilder.sum(productOrderJoin.get("totalPrice")))
                .where(criteriaBuilder.equal(orderRoot.get("user"), user),
                        criteriaBuilder.equal(orderRoot.get("orderStatus"), OrderStatus.COMPLETED));

        Double totalAmount = entityManager.createQuery(query).getSingleResult();
        return AchievementDtoRes.builder().ransomAmount(totalAmount).build();
    }
}
