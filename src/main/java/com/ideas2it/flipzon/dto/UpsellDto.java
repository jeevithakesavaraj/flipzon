package com.ideas2it.flipzon.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsellDto {
    private long productId;
    private long upsellProductId;
}
