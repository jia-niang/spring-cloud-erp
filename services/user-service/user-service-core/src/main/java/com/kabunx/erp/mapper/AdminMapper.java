package com.kabunx.erp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.model.AdminDO;
import com.kabunx.erp.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends PlusMapper<AdminDO> {
    AdminVO findByUserId(Long userId);
    List<AdminVO> selectWithUser(IPage<AdminVO> page);
}
