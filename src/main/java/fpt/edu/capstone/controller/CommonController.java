package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.service.FieldsService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/common")
@AllArgsConstructor
public class CommonController {
    private final FieldsService fieldsService;

    @GetMapping("/get-list-field")
    public ResponseData getListCareer(){
        try {
            List<Fields> careers = fieldsService.getAllField();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, careers);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/get-list-hashtag")
    public ResponseData getListHashtag(){
        //lấy ra được ngành nghề hiển thị trong thanh combobox
        return null;
    }
}
