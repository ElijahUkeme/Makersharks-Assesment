package com.makersharks.assessment.security.service.main.role;

import com.makersharks.assessment.exception.exeception.DataAlreadyExistException;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.security.dto.RoleDto;
import com.makersharks.assessment.security.dto.RoleToUserDto;

public interface RoleService {
    public String saveRole(RoleDto roleDto) throws DataAlreadyExistException;

    public String addRoleToUser(RoleToUserDto roleToUserDto) throws DataNotFoundException, DataAlreadyExistException;
}
