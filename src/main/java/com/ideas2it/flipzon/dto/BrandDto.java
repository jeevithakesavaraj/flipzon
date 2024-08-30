package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of Product Entity
 * @author Gokul
 */

@Data
@Builder
public class BrandDto {

    private Long id;

    private String name;
}
