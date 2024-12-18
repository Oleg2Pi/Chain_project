package by.polikarpov.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonsHomePageDto {

    private long chatId;
    private String firstName;
    private String lastName;
    private String imageFilePath;
    private int workId;
    private String workFile;
    private String workType;
}
