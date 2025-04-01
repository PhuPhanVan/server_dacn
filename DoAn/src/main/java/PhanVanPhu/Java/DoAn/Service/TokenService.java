//package PhanVanPhu.Java.DoAn.Service;
//
//import org.springframework.stereotype.Service;
//import io.agora.rtm.RtmTokenBuilder;
//import io.agora.rtm.RtmTokenBuilder.Role;
//@Service
//public class TokenService {
//
//    private final String APP_ID = "1f869c149ceb4751a16dfde616c2db4e";
//    private final String APP_CERTIFICATE = "YOUR_APP_CERTIFICATE"; // Thay thế bằng APP_CERTIFICATE thực tế
//
//    public String generateToken(String channelName, String uid) {
//        RtmTokenBuilder tokenBuilder = new RtmTokenBuilder();
//        return tokenBuilder.buildToken(APP_ID, APP_CERTIFICATE, uid, Role.Rtm_User, 3600);
//    }
//}
