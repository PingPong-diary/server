package pingpong.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pingpong.server.domain.UserDiary;

@Mapper
public interface UserDiaryMapper {
    List<UserDiary> getMembers(@Param("diaryId") int diaryId);

    void inviteMember(@Param("diaryId") int diaryId, @Param("userId") int userId);

    void removeMember(@Param("diaryId") int diaryId, @Param("userId") int userId);
}
