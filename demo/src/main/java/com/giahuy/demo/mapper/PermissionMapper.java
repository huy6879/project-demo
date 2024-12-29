package com.giahuy.demo.mapper;


import com.giahuy.demo.dto.request.PermissionRequest;
import com.giahuy.demo.dto.response.PermissionResponse;
import com.giahuy.demo.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
