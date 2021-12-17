package com.kabunx.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.service.PlusService;

public class PlusServiceImpl<M extends PlusMapper<T>, T> extends ServiceImpl<M, T>
        implements PlusService<T> {
}
