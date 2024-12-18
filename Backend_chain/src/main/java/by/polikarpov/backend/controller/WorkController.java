package by.polikarpov.backend.controller;

import by.polikarpov.backend.dto.WorkPageDto;
import by.polikarpov.backend.entity.Work;
import by.polikarpov.backend.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class WorkController {

    private final WorkService service;

    @Autowired
    public WorkController(WorkService service) {
        this.service = service;
    }

    @GetMapping("/work/{id}")
    public ResponseEntity<WorkPageDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findWorkAndWorksForPage(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Work>> findAllByExecutor(@PathVariable int id) {
        return ResponseEntity.ok(service.findAllByExecutorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Work> createWork(@RequestParam("executorId") Integer executorId,
                                           @RequestParam("name") String name,
                                           @RequestParam("dateAdded") Long dateAdded,
                                           @RequestParam("description") String description,
                                           @RequestParam("type") String type) {
        try {
            return ResponseEntity.ok(service.saveWork(
                    executorId, name, dateAdded, description, type
            ));
        } catch (Exception e) {
            // Здесь вы можете добавить более детальную обработку ошибок
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

}
