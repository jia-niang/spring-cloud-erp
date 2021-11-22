package com.kabunx.erp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabunx.erp.mapper.MemberMapper;
import com.kabunx.erp.model.Member;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.vo.MemberVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberMapper memberMapper;

    @Override
    public MemberVo findById(Integer id) {
        Member member = memberMapper.selectById(id);
        try {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(member.toString(), MemberVo.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
