package com.it;

import com.it.sys.entity.User;
import com.it.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ManageBackApplicationTests {

    // @Resource注解默认按照名称进行匹配,名称可以通过name属性进行指定，
    // 如果没有指定name属性，当注解写在字段上时，默认取字段名，按照名称查找,当找不到与名称匹配的bean时才按照类型进行装配。
    @Resource
    private UserMapper userMapper;

    @Test
    void testMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
