package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<UserWithNameAndUsernameProjection> findAllUsersWithNamesAndUsernamesProjectedBy();

    @Query(value = "SELECT user FROM User user WHERE user.name LIKE %:searchString% OR user.username LIKE " +
            "%:searchString%")
    List<UserWithNameAndUsernameProjection> findAllByNameSearchString(@Param("searchString") String searchString);

}
