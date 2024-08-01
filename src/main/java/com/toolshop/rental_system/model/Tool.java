package com.toolshop.rental_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tool {
    @Id
    private Long id;

    private String toolCode;

    @ManyToOne
    @JoinColumn(name = "type_info_id", nullable = false)
    private ToolTypeInfo typeInfo;

    private String brand;
}
