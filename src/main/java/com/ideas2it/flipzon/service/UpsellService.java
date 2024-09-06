package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.UpsellResponseDto;
import com.ideas2it.flipzon.dto.UpsellDto;

public interface UpsellService {
    UpsellResponseDto addUpsell(UpsellDto upsellDto);

    UpsellResponseDto deleteUpsell(UpsellDto upsellDto);
}