package ljakovic.simplebudgeting.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReqDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
