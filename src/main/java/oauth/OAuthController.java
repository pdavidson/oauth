package oauth;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class OAuthController {
    Splitter.MapSplitter authorizationSplitter = Splitter.on(",").trimResults().withKeyValueSeparator("=");
    Set<String> OAUTH_SIGNED_PARAMS = new HashSet<>(Arrays.asList(
            "oauth_consumer_key",
            "oauth_token",
            "oauth_signature_method",
            "oauth_timestamp",
            "oauth_nonce",
            "oauth_version"
    ));

//http://oauth.net/core/1.0a/#signing_process

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public ResponseEntity<String> doIt(HttpServletRequest request) {
        String authorization = Strings.nullToEmpty(request.getHeader("Authorization"));
        if (!authorization.toLowerCase().startsWith("oauth")) {
            return new ResponseEntity<>("Nope no valid auth header", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> authorizationMap = toAuthorizationMap(authorization);
        Multimap<String, String> signingMap = ArrayListMultimap.create();


        addParamsToSigningMap(signingMap, request.getParameterMap());
        addOAuthParamsToSigningMap(signingMap, authorizationMap);


        String response = "Success Brah";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void addParamsToSigningMap(Multimap<String, String> signingMap, Map<String, String[]> parameterMap) {
        parameterMap.entrySet().stream().forEach(entry ->
                Arrays.asList(entry.getValue())
                        .forEach(value -> signingMap.put(entry.getKey(), value)));
    }


    Map<String, String> toAuthorizationMap(String authorization) {
        return authorizationSplitter.split(authorization.substring(6));
    }

    void addOAuthParamsToSigningMap(Multimap<String, String> signingMap, Map<String, String> oauthParams){
        oauthParams.entrySet().stream()
                .filter(e -> OAUTH_SIGNED_PARAMS.contains(e.getKey()))
                .forEach(e -> signingMap.put(e.getKey(), e.getValue()));
    }


    String normal(Multimap<String, String> signingMap) {
        Multimaps.index(signingMap, )
    }

}
