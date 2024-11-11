package com.uken.api.rest.repostitory;

import com.uken.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}

