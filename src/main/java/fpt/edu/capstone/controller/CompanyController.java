package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Avatar;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.impl.ImageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/get-list-company")
    public ResponseData getListCompany(){
        try {
            List<Company> careers = companyService.getAllCompany();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, careers);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/find-company")
    public ResponseData findCompany(@RequestParam String companyName){
        return null;
    }

    @PostMapping("/create-company")
    public ResponseData createCompany(@RequestBody CreateCompanyRequest request){
        try {
            Company company = companyService.createCompany(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, company);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("update-avatar")
    public ResponseData updateCompanyAvatar(@RequestParam("file") MultipartFile file, long companyId) {
        try{
            Optional<Company> companySearched = companyService.findById(companyId);
            if(companySearched.isPresent()) {
                Optional<Image> imageSearched = imageService.findAvatarByCompanyId(companyId);
                if(imageSearched.isPresent()) {
                    imageService.updateAvatar(imageSearched.get().getId(), file);
                    return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "update avatar successful", imageSearched.get().getId());
                }else {
                    Image image = imageService.saveCompanyAvatar(file, "IMG", companyId);
                    return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Insert avatar successful", image.getId());
                }
            }else {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Can not find this company", companyId);
            }

        }catch (Exception ex){
            return null;
        }
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<byte[]> getCompanyAvatar(@PathVariable String id) {
        Optional<Image> image = imageService.finById(id);

        if (!image.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Image image1 = image.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image1.getName() + "\"")
                .contentType(MediaType.valueOf(image1.getContentType()))
                .body(image1.getData());
    }

}
