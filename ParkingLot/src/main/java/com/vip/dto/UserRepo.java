package com.vip.dto;

import com.vip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
//   @Query(value = "SELECT * FROM data_zenith.user where email =?0",nativeQuery = true)
    Optional<User> findByEmail(String email);

    Optional<User> findTop1ByEmail(String email);

    Optional<User> findByMobileNo(String phoneNumber);

    Optional<User> findTop1ByMobileNo(String mobileNo);
}
