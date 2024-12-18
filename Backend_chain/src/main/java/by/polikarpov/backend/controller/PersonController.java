package by.polikarpov.backend.controller;

import by.polikarpov.backend.dto.PersonProfileDto;
import by.polikarpov.backend.dto.PersonsHomePageDto;
import by.polikarpov.backend.entity.Person;
import by.polikarpov.backend.service.PersonService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController (PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/home")
    public ResponseEntity<List<PersonsHomePageDto>> getHomePerson() {
        return ResponseEntity.ok(service.findAllByHomePage());
    }
    
    @GetMapping("/{chatId}")
    public ResponseEntity<PersonProfileDto> findProfile(@PathVariable Long chatId) {
        return ResponseEntity.ok(service.findProfileByChatId(chatId));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPerson(
            @RequestParam("chatId") Long chatId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("usernameTG") String usernameTG,
            @RequestParam("phone") String phone,
            @RequestParam("work") String work
    ) {
        try {
            service.createPerson(chatId, firstName, lastName, usernameTG, phone, work);
            return ResponseEntity.ok().build();
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
