package fpt.edu.capstone.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCategoryRequest {
    @NotBlank(message = "Category name must not be blank")
    private String name;

    private String description;
}
