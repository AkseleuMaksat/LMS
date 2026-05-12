package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.user.RoleAssignRequest;
import kz.bitlab.lms.dto.user.UserRegisterRequest;
import kz.bitlab.lms.dto.user.UserUpdateRequest;

public interface UserService {
    void registerUser(UserRegisterRequest request);
    void updateUser(String username, UserUpdateRequest request);
    void assignRoles(String targetUsername, RoleAssignRequest request);
}
