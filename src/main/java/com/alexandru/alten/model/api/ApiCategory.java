package com.alexandru.alten.model.api;

import com.alexandru.alten.model.db.Category;
import com.alexandru.alten.model.form.FormCategory;

public class ApiCategory extends FormCategory {

    private final Long id;

    public ApiCategory(String name, Long id) {
        super(name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static ApiCategory fromDb(Category category) {
        return new ApiCategory(category.getName(), category.getId());
    }
}
