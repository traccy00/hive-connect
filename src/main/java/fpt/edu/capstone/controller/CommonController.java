package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.entity.VietnamCountry;
import fpt.edu.capstone.service.CountryService;
import fpt.edu.capstone.service.FieldsService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/common")
@AllArgsConstructor
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final FieldsService fieldsService;

    private final CountryService countryService;
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

    @GetMapping("/get-vietnam-country")
    public ResponseData getVietnamCountry(){
        try {
            List<VietnamCountry> careers = countryService.getListCountry();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, careers);
        }catch (Exception e){
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
