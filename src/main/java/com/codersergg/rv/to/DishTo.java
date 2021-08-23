package com.codersergg.rv.to;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class DishTo extends BaseTo implements Serializable {

    public DishTo(Integer id, String name, int prise) {
        super(id);
        this.name = name;
        this.prise = prise;
    }

    protected String name;
    private int prise;
}
