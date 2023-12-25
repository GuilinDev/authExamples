package com.guilin.idempotenceendpoint.controller;

import com.guilin.idempotenceendpoint.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/test")
    public String test() {
        return "Inside TokenController!";
    }

    /**
     * Get Token string
     * @return Token string
     */
    @GetMapping("/token/generate")
    public String getToken() {
        /**
         * Get user information (simulated data is used here)
         * Note: The content stored here is just an example.
         * Its function is to assist verification and make the verification logic safer.
         * - For example, the purpose of storing user information here is:
         * -- 1), use "token" to verify whether the corresponding Key exists in Redis
         * -- 2), use "user information" to verify whether the Value of Redis matches.
         */

        String userInfo = "test_user_info";
        // Generate Token string and return it
        log.info("User information: {}", userInfo);
        return tokenService.generateToken(userInfo);
    }

    /**
     * idempotence test interface
     *
     * @param token idempotence token string
     * @return result
     */
    @PostMapping("/token/verify")
    public String test(@RequestHeader(value = "token") String token) {
        // Get user information (simulated data is used here)
        String userInfo = "test_user_info";
        // Based on the Token string and user information,
        // verify whether the request is repeated in Redis
        boolean result = tokenService.validToken(token, userInfo);
        // Return the result
        return result ? "Normal Calling" : "Repetitive Calling";
    }
}