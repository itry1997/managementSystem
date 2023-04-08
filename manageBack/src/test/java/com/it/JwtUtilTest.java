package com.it;

import com.it.common.utils.JwtUtil;
import com.it.sys.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author cui
 * @create 2023--04--04--11:28
 */
@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("wangwu");
        user.setId(10086);
        user.setPhone("17340078857");
        String token = jwtUtil.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testCreateJWT2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjMTVkZTMxOC01MTUxLTRlMDQtOTdiNy1jNTUxZDc5ZjYwYTgiLCJzdWIiOiJ7XCJpZFwiOjEwMDg2LFwicGhvbmVcIjpcIjE3MzQwMDc4ODU3XCIsXCJ1c2VybmFtZVwiOlwid2FuZ3d1XCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjgwNTg3NzAyLCJleHAiOjE2ODA1ODk1MDJ9.aoGvteLN2jPg1Q6uN7tXvLHAeBaL8FGhOIIYSM3zKvc";
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
