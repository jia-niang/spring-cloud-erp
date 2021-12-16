package com.kabunx.erp.builder;

import com.kabunx.erp.mapper.MemberMapper;
import com.kabunx.erp.mapper.UserMapper;
import com.kabunx.erp.model.MemberDO;
import com.kabunx.erp.model.UserDO;
import com.kabunx.erp.query.Builder;
import com.kabunx.erp.relation.HasMany;
import com.kabunx.erp.relation.HasOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 非单例模式，暂不支持加入IoC容器（bean）
 */
@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserBuilder extends Builder<UserDO, UserBuilder> {

    public UserBuilder(UserMapper mapper) {
        super(mapper);
    }

    public UserBuilder loadMember(MemberMapper memberMapper) {
        return loadMember(memberMapper, true);
    }

    /**
     * 定义关联关系，并设置是否携带关联数据
     */
    public UserBuilder loadMember(MemberMapper memberMapper, Boolean carry) {
        String relationName = "member";
        hasOne(relationName, (HasOne<MemberDO, UserDO> relation) -> {
            relation.setRelatedArgs(memberMapper, "user_id");
            relation.setLocalCollect(UserDO::getId);
            relation.setRelatedGroupingBy(MemberDO::getUserId);
            relation.setIntegrate(UserDO::setMember);
        });
        if (carry) {
            return with(relationName);
        }
        return this;
    }

    public UserBuilder loadMembers(MemberMapper memberMapper) {
        return loadMembers(memberMapper, true);
    }

    public UserBuilder loadMembers(MemberMapper memberMapper, Boolean carry) {
        String relationName = "members";
        hasMany(relationName, (HasMany<MemberDO, UserDO> relation) -> {
            relation.setRelatedArgs(memberMapper, "user_id");
            relation.setLocalCollect(UserDO::getId);
            relation.setRelatedGroupingBy(MemberDO::getUserId);
            relation.setIntegrate(UserDO::setMembers);
        });
        if (carry) {
            return with(relationName);
        }
        return this;
    }
}
