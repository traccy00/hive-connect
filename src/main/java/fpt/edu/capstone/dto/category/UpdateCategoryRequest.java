package fpt.edu.capstone.dto.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateCategoryRequest {
    private long id;

    @NotBlank(message = "Category name must not be blank")
    private String name;

    private String description;
}
