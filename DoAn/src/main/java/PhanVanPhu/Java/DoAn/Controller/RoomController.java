//package PhanVanPhu.Java.DoAn.Controller;
//
//import PhanVanPhu.Java.DoAn.Model.Room;
//import PhanVanPhu.Java.DoAn.Service.RoomService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/rooms")
//public class RoomController {
//    private final RoomService roomService;
//
//    public RoomController(RoomService roomService) {
//        this.roomService = roomService;
//    }
//
//    @PostMapping("/join")
//    public Room joinRoom(@RequestBody Room room) {
//        return roomService.joinRoom(room);
//    }
//
//    @GetMapping("/members")
//    public List<Room> getMembers() {
//        return roomService.getMembers(); // Thay đổi theo phương thức trong service của bạn
//    }
//
//    @GetMapping("/display-name/{name}")
//    public String getDisplayName(@PathVariable String name) {
//        return roomService.getDisplayName(name);
//    }
//}