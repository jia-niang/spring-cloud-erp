package com.kabunx.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.model.Member;
import com.kabunx.erp.vo.MemberVO;

import java.util.List;

public interface MemberMapper extends BaseMapper<Member> {
    List<MemberVO> selectPageToMemberVO(IPage<MemberVO> page);
}
