package PhanVanPhu.Java.DoAn.Repository;

import PhanVanPhu.Java.DoAn.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

}
