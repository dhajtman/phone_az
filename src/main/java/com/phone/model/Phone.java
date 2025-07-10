package com.phone.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
//@Table(name = "PHONES")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}