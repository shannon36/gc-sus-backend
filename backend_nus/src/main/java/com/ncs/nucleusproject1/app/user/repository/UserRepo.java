package com.ncs.nucleusproject1.app.user.repository;

/*@author: Shannon Heng, 9 October 2023*/
/*modified on 14 Nov by Shannon to include user role*/

import com.ncs.nucleusproject1.app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    List<User> findAll();

    List<User> findByRoleind(String roleInd);

    Optional<User> findFirstById(String id);
    Optional<User> findFirstByName(String name);
    Optional<User> findFirstByEmail(String email);
    void deleteById(String id);

}
