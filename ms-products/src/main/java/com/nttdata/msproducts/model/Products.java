package com.nttdata.msproducts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    private Long id;

    private String indicatorProducts;

    private String descindicatorProducts;

    private int typeProducts;

    private String descTypeProducts;
}
