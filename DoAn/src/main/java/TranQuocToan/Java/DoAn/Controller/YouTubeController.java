package TranQuocToan.Java.DoAn.Controller;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

import TranQuocToan.Java.DoAn.Model.YouTubeItem;
import TranQuocToan.Java.DoAn.Service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@Controller
public class YouTubeController {

    @Autowired
    YouTubeService service;

    @RequestMapping(value={"/api/google-youtube-api"}, method=RequestMethod.GET)
    public @ResponseBody List<YouTubeItem> searchYouTube(
            @RequestParam(value="search", required=true) String search,
            @RequestParam(value="items", required=false, defaultValue="25") String items) {

        int max = Integer.parseInt(items);
        List<YouTubeItem> result = service.youTubeSearch(search, max);
        return result;
    }

    @RequestMapping(value={"/test-dummy-api"}, method=RequestMethod.GET)
    public @ResponseBody List<YouTubeItem> testSearchYouTube(
            @RequestParam(value="search", required=true) String search,
            @RequestParam(value="items", required=false, defaultValue="1") String items) {

        int max = Integer.parseInt(items);
        List<YouTubeItem> result = new ArrayList<>();

        result.add(new YouTubeItem("foobar", search, "far", "czar"));
        return result;
    }
}