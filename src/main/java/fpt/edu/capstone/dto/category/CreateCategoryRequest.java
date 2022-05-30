package fpt.edu.capstone.dto.category;

import fpt.edu.capstone.entity.sprint1.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCategoryRequest {
    @NotBlank(message = "Category name must not be blank")
    private String name;

    private String description;
}
