package com.it.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.common.vo.Result;
import com.it.sys.entity.User;
import com.it.sys.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cuiwei
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @GetMapping("/all")
//    public Result<List<User>> getAllUser(){
//        List<User> list = userService.list();
//        return Result.success(list);
//    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        // @RequestBody 接收前端传递给后端的json字符串中的数据(请求体中的数据)
        Map<String,Object> data = userService.login(user);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或者密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@Param("token") String token){
        //@Param的作用就是给参数命名 外部要取出传入的token值，只需取它的参数名“token”即可
        Map<String,Object> data = userService.getUserInfo(token);
        if(data!=null){
            return Result.success(data);
        }
        return Result.fail(20003,"用户信息获取失败");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        // @RequestHeader 将请求头中变量值映射到控制器的参数中
        userService.logout(token);
        return Result.success("注销成功");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserListPage(@RequestParam(value = "username",required = false) String username,
                                        @RequestParam(value = "phone",required = false) String phone,
                                        @RequestParam(value = "pageNo") Long pageNo,
                                        @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(username != null,User::getUsername,username);
        wrapper.eq(phone != null,User::getPhone,phone);
        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        // @RequestBody主要用来接收前端传递给后端的json字符串中的数据
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        // @RequestBody主要用来接收前端传递给后端的json字符串中的数据
        user.setPassword(null);
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        // @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return Result.success("删除用户成功");
    }

}
