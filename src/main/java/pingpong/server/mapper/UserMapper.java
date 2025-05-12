package pingpong.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pingpong.server.domain.User;

@Mapper
public interface UserMapper {
    User getUser(String email);
    void joinUser(User user);
    boolean isEmail(String email);
    void changePw(@Param("email") String email, @Param("password") String password);
    void deleteUser(String email); 
}