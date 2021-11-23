package com.kabunx.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.model.User;
import com.kabunx.erp.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过注解简单处理
     *
     * @param page    分页信息
     * @param userDTO 前端请求信息
     * @return 分页数据
     */
    @Select("select * from `sys_user_basic` where (`name` = #{ps.name} or `account` = #{ps.name})")
    IPage<UserVO> simpleSelectPage(IPage<UserVO> page, @Param("ps") UserDTO userDTO);

    /**
     * 复杂一些的xml查询
     *
     * @param page    分页信息
     * @param userDTO 前端请求信息
     * @return 分页数据
     */
    IPage<UserVO> xmlSelectPage(IPage<UserVO> page, @Param("ps") UserDTO userDTO);
}
