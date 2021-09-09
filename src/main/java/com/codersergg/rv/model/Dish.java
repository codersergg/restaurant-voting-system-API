package com.codersergg.rv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "created", "restaurant_id"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Dish extends BaseEntity implements Serializable {

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "prise")
    @Range(min = 0)
    private int prise;

    @NotNull
    @Column(name = "created")
    private LocalDate created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;
}
