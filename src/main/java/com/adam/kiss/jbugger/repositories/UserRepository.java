package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsername(String username);
    public List<UserWithNameAndUsernameProjection> findAllUsersWithNamesAndUsernamesProjectedBy();
}
