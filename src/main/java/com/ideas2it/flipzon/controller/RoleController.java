package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dto.RoleDto;
import com.ideas2it.flipzon.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("flipzon/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto) {
        RoleDto savedRoleDto = roleService.addRole(roleDto);
        return new ResponseEntity<>(savedRoleDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(int id) {
        RoleDto roleDto = roleService.getRoleById(id);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto) {
        RoleDto updatedRoleDto = roleService.updateRole(roleDto);
        return new ResponseEntity<>(updatedRoleDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
