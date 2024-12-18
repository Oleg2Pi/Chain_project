package by.polikarpov.backend.controller;

import by.polikarpov.backend.bean.HttpSessionBean;
import by.polikarpov.backend.entity.Person;
import by.polikarpov.backend.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final PersonService service;
    private final HttpSessionBean httpSessionBean;

    @Autowired
    public AuthController(PersonService service, HttpSessionBean httpSessionBean) {
        this.service = service;
        this.httpSessionBean = httpSessionBean;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Map<String, String> payload, HttpServletResponse response) {
        String chatId = payload.get("chatId");

        if (chatId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Person person = service.findById(Long.valueOf(chatId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        httpSessionBean.setChatId(Long.valueOf(chatId));

        Cookie cookie = new Cookie("sessionId", Long.toString(chatId));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("profile")
    public ResponseEntity<Long> getChatId() {
        Long chatId = httpSessionBean.getChatId();

        if (chatId == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Person person = service.findById(chatId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(chatId);
    }

}
