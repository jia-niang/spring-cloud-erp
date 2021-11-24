package com.kabunx.erp.service.impl;

import com.kabunx.erp.converter.ObjectConverter;
import com.kabunx.erp.mapper.MemberMapper;
import com.kabunx.erp.model.MemberDO;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.vo.MemberVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberMapper memberMapper;

    @Override
    public MemberVO findById(Integer id) {
        MemberDO memberDO = memberMapper.selectById(id);
        return ObjectConverter.map(memberDO, MemberVO.class);
    }

    @Override
    public MemberVO findByIdWithUser(Integer id) {
        return memberMapper.findByIdWithUser(id);
    }
}
