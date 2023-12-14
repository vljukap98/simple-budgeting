package ljakovic.simplebudgeting.appuser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String dateCreated;
    private String dateModified;
}
