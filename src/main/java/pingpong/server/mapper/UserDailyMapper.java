package pingpong.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pingpong.server.domain.UserDaily;

@Mapper
public interface UserDailyMapper {

    List<UserDaily> getMembers(@Param("dailyId") int dailyId);

    void inviteMember(@Param("dailyId") int dailyId, @Param("userId") int userId);

    void removeMember(@Param("dailyId") int dailyId, @Param("userId") int userId);

    UserDaily getUserDaily(@Param("dailyId") int dailyId, @Param("userId") int userId);

    boolean isUserInDaily(@Param("dailyId") int dailyId, @Param("userId") int userId);

    void inviteMemberAsOwner(@Param("dailyId") int dailyId, @Param("userId") int userId);
}
