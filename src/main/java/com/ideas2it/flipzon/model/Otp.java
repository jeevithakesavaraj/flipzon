package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "OTPs")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mailId;

    private String password;

    private String phoneNumber;

    private String idProof;

    private String otp;
}
