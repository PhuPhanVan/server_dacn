package PhanVanPhu.Java.DoAn.Repository;

import PhanVanPhu.Java.DoAn.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByUid(String uid);
}