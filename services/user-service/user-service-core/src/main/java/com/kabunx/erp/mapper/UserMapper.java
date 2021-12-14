package com.kabunx.erp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.domain.dto.UserDTO;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.model.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends PlusMapper<UserDO> {

    UserDO findById(Long id);

    /**
     * 复杂一些的xml查询
     *
     * @param page    分页信息
     * @param userDTO 前端请求信息
     * @return 分页数据
     */
    IPage<UserDO> xmlSelectPage(IPage<UserDO> page, @Param("ps") UserDTO userDTO);
}
