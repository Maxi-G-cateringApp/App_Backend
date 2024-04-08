//package com.catering_app.Catering_app.controller.authController;
//
//import com.catering_app.Catering_app.model.MessageDto;
//import com.catering_app.Catering_app.model.TokenDto;
//import com.catering_app.Catering_app.model.UrlDto;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//
//@RestController
//public class GoogleAuthController {
//    @Value("${spring.security.oauth2.resource-server.opaque-token.client-id}")
//    private String clientId;
//    @Value("${spring.security.oauth2.resource-server.opaque-token.client_secret}")
//    private String clientSecret;
//
//    @GetMapping("/auth/url")
//    public ResponseEntity<UrlDto>auth(){
//        String url = new GoogleAuthorizationCodeRequestUrl(
//                clientId,
//                "http://localhost:4200",
//                Arrays.asList("email","profile","openid")
//        ).build();
//        System.out.println(url);
//        return ResponseEntity.ok(new UrlDto(url));
//    }
//
//    @GetMapping("/auth/callback")
//    public ResponseEntity<TokenDto> callBack(@RequestParam("code") String code){
//        String token;
//        try {
//             token = new GoogleAuthorizationCodeTokenRequest(
//                    new NetHttpTransport(),
//                    new GsonFactory(),
//                    clientId,
//                    clientSecret,
//                    code,
//                    "http://localhost:4200"
//            ).execute().getAccessToken();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        return ResponseEntity.ok(new TokenDto(token));
//    }
//
//    @GetMapping("/message")
//    public ResponseEntity<MessageDto>privateMessage(@AuthenticationPrincipal(expression = "name") String name){
//        return ResponseEntity.ok(new MessageDto("private content"+name));
//    }
//
//}
