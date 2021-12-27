package com.kabunx.erp.builder;

import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import com.kabunx.erp.mapper.MemberMapper;
import com.kabunx.erp.mapper.RoleMapper;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.MemberDO;
import com.kabunx.erp.model.RoleDO;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.query.Builder;
import com.kabunx.erp.relation.BelongsToMany;
import com.kabunx.erp.relation.HasMany;
import com.kabunx.erp.relation.HasOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * 非单例模式，暂不支持加入IoC容器（bean）
 */
@Slf4j
@Service
public class UserBuilder extends Builder<UserDO, UserBuilder> {

    @Resource
    UserMapper plusMapper;

    @Resource
    MemberMapper memberMapper;

    @Resource
    RoleMapper roleMapper;

    @Override
    public PlusMapper<UserDO> getMapper() {
        return plusMapper;
    }

    public <T> UserBuilder loadMember() {
        return loadMember(true, null);
    }

    public <T> UserBuilder loadMember(Consumer<PlusWrapper<MemberDO>> filter) {
        return loadMember(true, filter);
    }

    /**
     * 定义关联关系，并设置是否携带关联数据
     */
    public <T> UserBuilder loadMember(Boolean carry, Consumer<PlusWrapper<MemberDO>> filter) {
        String relationName = "member";
        hasOne(relationName, (HasOne<MemberDO, UserDO> relation) -> {
            relation.setRelatedArgs(memberMapper, "user_id");
            relation.setLocalCollect(UserDO::getId);
            relation.setRelatedGroupingBy(MemberDO::getUserId);
            relation.setMerge(UserDO::setMember);
        });
        if (carry) {
            return with(relationName);
        }
        return this;
    }

    public UserBuilder loadMembers() {
        return loadMembers(true, null);
    }

    public UserBuilder loadMembers(Consumer<PlusWrapper<MemberDO>> filter) {
        return loadMembers(true, filter);
    }

    public UserBuilder loadMembers(Boolean carry, Consumer<PlusWrapper<MemberDO>> filter) {
        String relationName = "members";
        hasMany(relationName, (HasMany<MemberDO, UserDO> relation) -> {
            relation.setRelatedArgs(memberMapper, "user_id");
            relation.setLocalCollect(UserDO::getId);
            relation.setRelatedGroupingBy(MemberDO::getUserId);
            relation.setMerge(UserDO::setMembers);
            if (filter != null) {
                relation.filter(filter);
            }
        });
        if (carry) {
            return with(relationName);
        }
        return this;
    }

    public UserBuilder loadRoles() {
        return loadRoles(true, null);
    }

    public UserBuilder loadRoles(Consumer<PlusWrapper<RoleDO>> filter) {
        return loadRoles(true, filter);
    }

    public UserBuilder loadRoles(Boolean carry, Consumer<PlusWrapper<RoleDO>> filter) {
        String relationName = "roles";
        belongsToMany(relationName, (BelongsToMany<RoleDO, UserDO> relation) -> {
            relation.setRelatedArgs(roleMapper, "user_role", "user_id", "role_id");
            relation.setLocalCollect(UserDO::getId);
            relation.setRelatedGroupingBy(RoleDO::getPivotForeignId);
            relation.setMerge(UserDO::setRoles);
            if (filter != null) {
                relation.filter(filter);
            }
        });
        if (carry) {
            return with(relationName);
        }
        return this;
    }
}
