package com.it.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.common.vo.Result;
import com.it.sys.entity.Role;
import com.it.sys.service.IRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/all")
    public Result<List<Role>> getAllRole(){
        List<Role> roleList = roleService.list();
        return Result.success(roleList);
    }

    // 获取角色列表
    @GetMapping("/list")
    public Result<Map<String,Object>> getRoleListPage(@RequestParam(value = "roleName",required = false) String roleName,
                                                      @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(roleName != null,Role::getRoleName,roleName);
        // 排序
        wrapper.orderByDesc(Role::getRoleId);
        Page<Role> page = new Page<>(pageNo, pageSize);
        roleService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    // 添加角色
    @PostMapping
    public Result<?> addRole(@RequestBody Role role){
        // @RequestBody主要用来接收前端传递给后端的json字符串中的数据
        roleService.addRole(role);
        return Result.success("新增角色成功");
    }

    // 更新角色
    @PutMapping
    public Result<?> updateRole(@RequestBody Role role){
        // @RequestBody主要用来接收前端传递给后端的json字符串中的数据
        roleService.updateRole(role);
        return Result.success("修改角色成功");
    }

    // 根据id查询角色
    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable("id") Integer id){
        // @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中
        Role role = roleService.getRoleById(id);
        return Result.success(role);
    }

    // 删除角色
    @DeleteMapping("/{id}")
    public Result<Role> deleteRoleById(@PathVariable("id") Integer id){
        roleService.deleteRoleById(id);
        return Result.success("删除角色成功");
    }
}
