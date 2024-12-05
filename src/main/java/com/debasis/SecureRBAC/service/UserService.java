package com.debasis.SecureRBAC.service;

import com.debasis.SecureRBAC.model.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    User assignRoleToUser(Long userId, String roleName);
    User removeRoleFromUser(Long userId, String roleName);
    void changePassword(String username, String oldPassword, String newPassword);

}
