package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.category.CreateCategoryRequest;
import fpt.edu.capstone.dto.category.UpdateCategoryRequest;
import fpt.edu.capstone.entity.Category;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.CategoryService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create-category")
    @Operation(summary = "create category")
    public ResponseData createCategory (@RequestBody @Valid CreateCategoryRequest request){
        try {
            Optional<Category> category = categoryService.findCategoryByName(request.getName());
            if(category.isPresent()){
                throw new HiveConnectException("Category name: "+request.getName() + " already exist!");
            }
            Object CreateCategoryRequest = request;
            Category categoryRequest = modelMapper.map(CreateCategoryRequest, Category.class);
            categoryService.saveCategory(categoryRequest);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.CREATE_CATEGORY_SUCCESS);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/list-category")
    @Operation(summary = "get list category")
    public ResponseData getListCategory (@RequestParam(value = "name", defaultValue = StringUtils.EMPTY) String name){
        try {
            List<Category> categoryList = categoryService.searchCategoryList(name);
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

    @PutMapping("/update-category")
    @Operation(summary = "update category")
    public ResponseData updateCategory(@RequestBody @Valid UpdateCategoryRequest request){
        try {
            categoryService.updateCategory(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.UPDATE_CATEGORY_SUCCESS);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @DeleteMapping("/delete-category")
    @Operation(summary = "delete category")
    public ResponseData deleteCategory(@RequestParam(value = "id") long categoryId){
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.DELETE_CATEGORY_SUCCESS);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
