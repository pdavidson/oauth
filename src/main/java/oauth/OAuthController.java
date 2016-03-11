package oauth;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class OAuthController {
    Splitter.MapSplitter authorizationSplitter = Splitter.on(",").trimResults().withKeyValueSeparator("=");

//http://oauth.net/core/1.0a/#signing_process

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public ResponseEntity<String> doIt(HttpServletRequest request) {
        String authorization = Strings.nullToEmpty(request.getHeader("Authorization"));
        if (!authorization.toLowerCase().startsWith("oauth")) {
            return new ResponseEntity<String>("Nope no valid auth header", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> authorizationMap = toAuthorizationMap(authorization);
        


        Map<String, String[]> parameterMap = request.getParameterMap();


        String response = "Success Brah";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    Map<String, String> toAuthorizationMap(String authorization) {
        return authorizationSplitter.split(authorization.substring(6));
    }

}
