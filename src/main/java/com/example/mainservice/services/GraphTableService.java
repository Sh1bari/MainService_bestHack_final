package com.example.mainservice.services;

import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.Product;
import com.example.mainservice.models.entities.ProductOrder;
import com.example.mainservice.models.models.SumResult;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import com.example.mainservice.models.models.responses.ProductDtoRes;
import com.example.mainservice.repositories.OrderRepo;
import com.example.mainservice.specifications.OrderGraphSpecification;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraphTableService {
    private final ProductService productService;
    private final OrderRepo orderRepo;

    public List<GraphTableDataElementDto> getGraphTableDataElements(Specification<Order> spec){
        List<GraphTableDataElementDto> res = new ArrayList<>();
        List<Product> products = productService.getAll();
        for (Product product : products){
            ProductDtoRes productDto = ProductDtoRes.mapFromEntity(product);
            List<ProductOrder> filteredOrders = orderRepo.findAll(spec).stream()
                    .filter(order -> order.getProductOrders()
                            .stream()
                            .anyMatch(productOrder -> productOrder.getProduct().equals(product)))
                    .flatMap(order -> order.getProductOrders().stream())
                    .toList();


            SumResult sumResult = filteredOrders.stream()
                    .collect(SumResult::new,
                            SumResult::accumulate,
                            SumResult::combine);
            res.add(new GraphTableDataElementDto(productDto,
                    sumResult.getTotalAmount(),
                    sumResult.getTotalSpend()));
        }
        return res;
    }
}
