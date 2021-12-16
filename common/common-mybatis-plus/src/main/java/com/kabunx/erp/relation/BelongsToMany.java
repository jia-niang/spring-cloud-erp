package com.kabunx.erp.relation;

import com.kabunx.erp.extension.mapper.PlusMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * 多对多关系存在连接表
 * user(id, name), user_role(user_id, role_id), role(id, name)
 * 一个用户可以拥有多个角色，一个角色可以属于多个用户
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class BelongsToMany<TC, TP> extends Relation<TC, TP, BelongsToMany<TC, TP>> {
    private final static String name = "belongToMany";

    /*
     * 连接表， 类比为"user_role"
     */
    protected String table;

    /**
     * 关联的模型在连接表里的外键名，类比为"user_role(user_id)"
     */
    protected String foreignPivotKey;

    /**
     * 另一个模型在连接表里的外键名，类比为"user_role(role_id)"
     */
    protected String relatedPivotKey;

    /**
     * 当前模型的键名，类比为"user(id)"
     */
    protected String parentKey = "id";

    /**
     * 关联模型的主键，类比为"role(id)"
     */
    protected String relatedKey = "id";

    /**
     * 自定义回调，关联数据回填到主属性中
     */
    private BiConsumer<TP, List<TC>> integrate;

    public BelongsToMany(PlusMapper<TP> parent) {
        super(parent);
    }

    @Override
    public void initRelation(List<TP> records) {
        if (!requiredConditions()) {
            return;
        }
        initRelatedData(records);
    }

    private Boolean requiredConditions() {
        return requiredRelatedArgs() && integrate != null;
    }
}
