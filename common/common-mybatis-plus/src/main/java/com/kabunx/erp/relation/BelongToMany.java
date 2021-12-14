package com.kabunx.erp.relation;

import com.kabunx.erp.extension.mapper.PlusMapper;

import java.util.List;

public class BelongToMany<TC, TP> extends Relation<TC, TP> {
    public BelongToMany(PlusMapper<TC> mapper, PlusMapper<TP> parent) {
        super(mapper, parent);
        this.name = "belongToMany";
    }

    @Override
    public void initRelation(List<TP> records) {

    }
}
