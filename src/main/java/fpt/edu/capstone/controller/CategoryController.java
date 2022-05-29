package fpt.edu.capstone.controller;

import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.category.CreateCategoryRequest;
import fpt.edu.capstone.entity.sprint1.Category;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.CategoryService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create-category")
    @Operation(summary = "create category")
    public ResponseData createCategory (@RequestBody @Valid CreateCategoryRequest request){
        try {
            Optional<Category> category = categoryService.findCategoryByName(request.getName());
            if(category.isPresent()){
                throw new HiveConnectException("Category name: "+request.getName() + " already exist!");
            }
            Category saveCategory = request.toEntity();
            categoryService.saveCategory(saveCategory);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.CREATE_CATEGORY_SUCCESS);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/list-category")
    @Operation(summary = "get list category")
    public ResponseData getListCategory (@RequestParam("name") String name){
        try {
            List<Category> categoryList = categoryService.getListCategory(name);
            if(categoryList.isEmpty()){
                throw new HiveConnectException("List category empty");
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, categoryList);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
