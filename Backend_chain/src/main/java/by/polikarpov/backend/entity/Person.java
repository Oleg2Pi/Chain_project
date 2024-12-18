package by.polikarpov.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"image", "executor"})
public class Person {

    @Id
    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username_tg")
    private String usernameTG;

    private String phone;

    @OneToOne(mappedBy = "person")
    @JsonManagedReference
    private ImageOfPerson image;

    @OneToOne(mappedBy = "person")
    @JsonManagedReference
    private Executor executor;

}
