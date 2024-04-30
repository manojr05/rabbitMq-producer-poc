package com.aimscore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gateway")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Gateway {
    @Id
    private String macId;
    private String uri;
}
