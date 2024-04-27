package com.example.mainservice.services;

import com.example.mainservice.models.entities.Product;
import com.example.mainservice.models.entities.ProductOrder;
import com.example.mainservice.models.models.SumResult;
import com.example.mainservice.models.models.responses.GraphTableDataElementDto;
import com.example.mainservice.models.models.responses.ProductDtoRes;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphTableService {
    private final ProductService productService;

    public List<GraphTableDataElementDto> getGraphTableDataElements(){
        List<GraphTableDataElementDto> res = new ArrayList<>();
        List<Product> products = productService.getAll();
        for (Product product : products){
            ProductDtoRes productDto = ProductDtoRes.mapFromEntity(product);
            List<ProductOrder> orders = product.getProductOrders();

            SumResult sumResult = orders.stream()
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
