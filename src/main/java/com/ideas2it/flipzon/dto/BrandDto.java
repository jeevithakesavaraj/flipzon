package com.ideas2it.flipzon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object of Product Entity
 *
 * @author Gokul
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private long id;

    private String name;
}
