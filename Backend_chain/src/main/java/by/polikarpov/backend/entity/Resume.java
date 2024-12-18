package by.polikarpov.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"executor", "activityArea", "userStatus", "workExperience"})
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "information_yourself")
    private String informationYourself;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    @JsonBackReference
    private Executor executor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_area_id")
    @JsonManagedReference
    private ActivityArea activityArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id")
    @JsonManagedReference
    private UserStatus userStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_experience_id")
    @JsonManagedReference
    private WorkExperience workExperience;

}
