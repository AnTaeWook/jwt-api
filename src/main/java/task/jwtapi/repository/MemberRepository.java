package task.jwtapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task.jwtapi.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByMemberId(String username);
}
