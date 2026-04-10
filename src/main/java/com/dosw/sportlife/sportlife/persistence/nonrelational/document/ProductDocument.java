package com.dosw.sportlife.sportlife.persistence.nonrelational.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductDocument {

    @Id
    private String id;

    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer stock;
    private List<String> images;
    private String status;
}
