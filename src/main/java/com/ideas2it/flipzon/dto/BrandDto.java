package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of Product Entity
 * @author Gokul
 */

@Data
@Builder
@Getter
@Setter
public class BrandDto {

    private Long id;

    private String name;
}
