package task.jwtapi.dto;

import lombok.Data;
import task.jwtapi.domain.Role;

@Data
public class MemberJoinRequestDto {

    String memberId;
    String password;
    Role role;
}
