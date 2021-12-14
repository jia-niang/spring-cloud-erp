package com.kabunx.erp.relation;

import com.kabunx.erp.extension.mapper.PlusMapper;

import java.util.List;

public class BelongTo<TC, TP> extends Relation<TC, TP> {
    public BelongTo(PlusMapper<TC> mapper, PlusMapper<TP> parent) {
        super(mapper, parent);
        this.name = "belongTo";
    }

    @Override
    public void initRelation(List<TP> records) {

    }
}
