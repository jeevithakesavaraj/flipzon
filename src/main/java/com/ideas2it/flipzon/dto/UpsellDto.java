package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsellDto {
    @NotNull
    private long upsellProductId;
}
