package com.kabunx.erp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.model.MemberDO;
import com.kabunx.erp.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper extends PlusMapper<MemberDO> {
    List<MemberVO> selectWithUser(IPage<MemberVO> page);

    MemberVO findByUserIdWithUser(Long userId);
}
