package by.polikarpov.backend.controller;

import by.polikarpov.backend.exceptions.FileSaveException;
import by.polikarpov.backend.service.FilesService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final FilesService service;

    @Autowired
    public FilesController(FilesService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> saveFile(@RequestParam("workId") Integer workId,
                                           @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            service.saveWork(file, workId);
            return ResponseEntity.ok().build();
        } catch (FileSaveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    @PostMapping("/image")
    public ResponseEntity<String> saveImage(
            @RequestParam("chatId") Long chatId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("typeFile") String typeFile
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            service.saveImagePerson(file, chatId, typeFile);
            return ResponseEntity.ok().build();
        } catch (FileSaveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id) {
        try {
            Path path = Paths.get(service.getWorkFilePath(id));
            Resource resource = new org.springframework.core.io.FileSystemResource(path);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                            resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/person/{chatId}")
    public ResponseEntity<Resource> getImagePerson(@PathVariable Long chatId) {
        try {
            Path path = Paths.get(service.getPersonImageFilePath(chatId));
            Resource resource = new org.springframework.core.io.FileSystemResource(path);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                            resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
