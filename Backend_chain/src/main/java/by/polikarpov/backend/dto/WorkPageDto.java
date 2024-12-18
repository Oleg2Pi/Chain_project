package by.polikarpov.backend.dto;

import by.polikarpov.backend.entity.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPageDto {

    private long personChatId;
    private String personFirstName;
    private String personLastName;
    private String personImagePath;
    private String executorActivityArea;
    private String executorStatusCategory;
    private int workId;
    private String workFilePath;
    private String workName;
    private String workDescription;
    private List<Work> otherWorks;
}
