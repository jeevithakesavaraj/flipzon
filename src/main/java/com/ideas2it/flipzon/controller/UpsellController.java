package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dto.UpsellDto;
import com.ideas2it.flipzon.dto.UpsellResponseDto;
import com.ideas2it.flipzon.service.UpsellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flipzon/api/v1/upsell")
public class UpsellController {

    @Autowired
    private UpsellService upsellService;

    @PostMapping
    public ResponseEntity<UpsellResponseDto> addUpsell(@RequestBody UpsellDto upsellDto) {
        return ResponseEntity.ok(upsellService.addUpsell(upsellDto));
    }
}
