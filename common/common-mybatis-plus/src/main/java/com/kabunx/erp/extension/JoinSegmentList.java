package com.kabunx.erp.extension;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.segments.AbstractISegmentList;

import java.util.List;

public class JoinSegmentList extends AbstractISegmentList {
    @Override
    protected boolean transformList(List<ISqlSegment> list, ISqlSegment firstSegment, ISqlSegment lastSegment) {
        return false;
    }

    @Override
    protected String childrenSqlSegment() {
        return null;
    }
}
