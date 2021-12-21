package com.kabunx.erp.model;

import com.kabunx.erp.pojo.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PivotDO extends BaseDO {

    Long pivotForeignKey;

    Long pivotRelatedKey;
}
