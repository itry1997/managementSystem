package com.it.sys.mapper;

import com.it.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cuiwei
 * @since 2023-03-14
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Integer> getMenuIdListByRoleId(Integer id);
}
