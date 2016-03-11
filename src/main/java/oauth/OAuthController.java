package oauth;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OAuthController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public ResponseEntity<String> doIt(HttpServletRequest request) {
        String authorization = Strings.nullToEmpty(request.getHeader("Authorization"));
        if (!authorization.toLowerCase().startsWith("oauth")) {
            return new ResponseEntity<String>("Nope no valid auth header", HttpStatus.BAD_REQUEST);
        }


        String response = "Success Brah";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
