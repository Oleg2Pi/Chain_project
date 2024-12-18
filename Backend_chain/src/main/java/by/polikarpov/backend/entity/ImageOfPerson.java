package by.polikarpov.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "image_of_person")
@ToString(exclude = "person")
public class ImageOfPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_path")
    private String filePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_chat_id")
    @JsonBackReference
    private Person person;

}
