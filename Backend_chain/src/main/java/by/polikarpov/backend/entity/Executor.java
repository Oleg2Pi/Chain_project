package by.polikarpov.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"person", "resume", "works"})
public class Executor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_chat_id")
    @JsonBackReference
    private Person person;

    @OneToOne(mappedBy = "executor")
    @JsonManagedReference
    private Resume resume;

    @OneToMany(mappedBy = "executor")
    @JsonManagedReference
    private List<Work> works;
}
