package com.pigeonskyracespringsecurity.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pigeonskyracespringsecurity.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pigeons")
public class Pigeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "ringNumber  must be entered")
    private String ringNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Gender gender;

    @Min(value = 1, message = "Pigeon age must be at least 1")
    @Column(nullable = false)
    private int age;

    @NotBlank(message = "Pigeon color  must be entered")
    @Column(nullable = false)
    private String color;

    private String image;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
