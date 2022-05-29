package fpt.edu.capstone.dto.category;

import fpt.edu.capstone.entity.sprint1.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequest {
    @NotBlank(message = "Category name must not be blank")
    private String name;

    private String description;

    public static CreateCategoryRequest fromEntity(Category entity){
        if(entity == null){
            return null;
        }
        return CreateCategoryRequest.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
    public Category toEntity(){
        return Category.builder()
                .name(this.getName())
                .description(this.getDescription())
                .build();
    }
}
