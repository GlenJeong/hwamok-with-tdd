package com.hwamok.user.service;


import com.hwamok.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User create(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday, MultipartFile profilePicture) throws Exception;

    User getUser(long id);

    User updateProfile(String loginId, String password, String email, String nickname, String name, String userStatus, String birthday, MultipartFile profilePicture) throws Exception;

    User updateProfile(long id, String loginId, String password, String email, String nickname, String name, String userStatus, String birthday, MultipartFile profilePicture) throws Exception;

    void withdraw(long id);
}
