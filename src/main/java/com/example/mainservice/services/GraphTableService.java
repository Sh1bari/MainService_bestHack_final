package com.example.mainservice.services;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.Product;
import com.example.mainservice.models.entities.ProductOrder;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.models.models.SumResult;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import com.example.mainservice.models.models.responses.ProductDtoRes;
import com.example.mainservice.repositories.OrderRepo;
import com.example.mainservice.specifications.OrderGraphSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraphTableService {
    private final ProductService productService;
    private final OrderRepo orderRepo;

    private final EntityManager entityManager;

    @Transactional
    public List<GraphTableDataElementDto> getGraphTableDataElements(LocalDateTime startTime, LocalDateTime endTime, String region){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GraphTableDataElementDto> query = cb.createQuery(GraphTableDataElementDto.class);
        Root<Product> rootProduct = query.from(Product.class);
        Join<Product, ProductOrder> joinProductOrder = rootProduct.join("productOrders", JoinType.LEFT);
        Join<ProductOrder, Order> joinOrder = joinProductOrder.join("order", JoinType.LEFT);

        query.multiselect(
                rootProduct.alias("product"),
                cb.sum(joinProductOrder.get("amount")),
                cb.sum(joinProductOrder.get("totalPrice"))
        ).groupBy(rootProduct.get("id"));

        Predicate predicate = cb.conjunction();
        if (startTime != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(joinOrder.get("orderTime"), startTime));
        }
        if (endTime != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(joinOrder.get("orderTime"), endTime));
        }
        if (region != null) {
            predicate = cb.and(predicate, cb.equal(joinOrder.get("region").get("name"), region));
        }
        predicate = cb.and(predicate, cb.equal(joinOrder.get("orderStatus"), OrderStatus.COMPLETED));

        query.where(predicate);


        List<GraphTableDataElementDto> results = entityManager.createQuery(query).getResultList();
        return results;
    }
}
