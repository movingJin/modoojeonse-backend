package com.modoojeonse.member.repository;

import com.modoojeonse.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String userId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String phone);
}

