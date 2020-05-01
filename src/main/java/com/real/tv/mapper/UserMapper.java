package com.real.tv.mapper;

import com.real.tv.dto.User;
import com.real.tv.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapstruct utility mapper to handle entity and dto conversions
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(User user);

    User toUserDto(UserEntity userEntity);

    @IterableMapping(qualifiedByName = "toFee")
    public abstract List<User> toUsers(Iterable<UserEntity> userEntities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    public abstract void updateExistingUserEntity(UserEntity sourceEntity, @MappingTarget UserEntity targetEntity);
}
