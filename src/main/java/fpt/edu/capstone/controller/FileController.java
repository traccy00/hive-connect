package fpt.edu.capstone.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fpt.edu.capstone.dto.File.FileResponse;
import fpt.edu.capstone.entity.Avatar;
import fpt.edu.capstone.service.impl.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final UserImageService userImageService;

    @Autowired
    public FileController(UserImageService userImageService) {
        this.userImageService = userImageService;
    }

    @PostMapping("/import-file")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            userImageService.saveFile(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping("/get")
    public List<FileResponse> list() {
        return userImageService.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(Avatar avatar) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(avatar.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(avatar.getId());
        fileResponse.setName(avatar.getName());
        fileResponse.setContentType(avatar.getContentType());
        fileResponse.setSize(avatar.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<Avatar> fileEntityOptional = userImageService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Avatar avatar = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getName() + "\"")
                .contentType(MediaType.valueOf(avatar.getContentType()))
                .body(avatar.getData());
    }
}
