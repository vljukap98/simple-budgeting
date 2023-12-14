package ljakovic.simplebudgeting.appuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String dateCreated;
    private String dateModified;
}
