package by.polikarpov.backend.dto;

import by.polikarpov.backend.entity.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonProfileDto {

    private Long personChatId;
    private String personFirstName;
    private String personImagePath;
    private String executorActivityArea;
    private String executorStatusCategory;
    private List<Work> works;
    private Integer executorId;
}
