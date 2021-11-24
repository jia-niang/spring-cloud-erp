package com.kabunx.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.model.MemberDO;
import com.kabunx.erp.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<MemberDO> {
    List<MemberVO> selectWithUser(IPage<MemberVO> page);

    MemberVO findByIdWithUser(Integer id);
}
