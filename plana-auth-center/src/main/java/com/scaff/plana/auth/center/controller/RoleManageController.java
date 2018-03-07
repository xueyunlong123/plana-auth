package com.scaff.plana.auth.center.controller;

import com.scaff.common.web.argsresolver.FastJson;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.role.AddRoleReqDTO;
import com.scaff.dto.role.AssignPermissionReqDTO;
import com.scaff.model.Role;
import com.scaff.model.UrlPermission;
import com.scaff.utils.exception.WebBusinessCodeEnum;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/9/18.
 */
@RestController
@Slf4j
@RequestMapping("/roles")
public class RoleManageController {

    @Autowired
    private Datastore datastore;

    @GetMapping
    public JSONResult getRoles(){
        return JSONResult.ok(datastore.createQuery(Role.class).asList());
    }
    @PostMapping
    public JSONResult addRole(@FastJson AddRoleReqDTO dto){
        if (datastore.createQuery(Role.class).field("role_type").equal(dto.getRoleType()).count() > 0){
            return JSONResult.error(WebBusinessCodeEnum.ROLE_ALREADY_EXSIT);
        }
        datastore.save(
                Role.builder()
                        .roleType(dto.getRoleType())
                        .height(dto.getHeight())
                        .build()
        );
        return JSONResult.ok();
    }
    @DeleteMapping("/{roleId}")
    public JSONResult deleteRole(@PathVariable String roleId){
        Query<Role> query = datastore.createQuery(Role.class).field("_id").equal(new ObjectId(roleId));
        datastore.delete(query);
        return JSONResult.ok();
    }

    @PostMapping("/{roleId}/assignPermission")
    public JSONResult assignPermission(@PathVariable("roleId") String roleId, @FastJson AssignPermissionReqDTO dto){

        Query<Role> query = datastore.createQuery(Role.class)
                .field("_id").equal(new ObjectId(roleId));

        UpdateOperations<Role> updateOperations = datastore.createUpdateOperations(Role.class)
                .set("hold_permission_ID",dto.getPermissions());

        datastore.update(query,updateOperations);

        return JSONResult.ok();
    }

    @GetMapping("/{roleId}/permissions")
    public JSONResult getRolePermissions(@PathVariable("roleId") String roleId){
        Role role = datastore.createQuery(Role.class).field("_id").equal(new ObjectId(roleId)).get();
        List<UrlPermission> urlPermissions = datastore.createQuery(UrlPermission.class).field("id").in(
                role.getHoldPermissionID().stream().map(ObjectId::new).collect(Collectors.toList())
        ).asList();
        return JSONResult.ok(urlPermissions);
    }

}
