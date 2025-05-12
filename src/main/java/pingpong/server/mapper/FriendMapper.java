package pingpong.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pingpong.server.domain.Friend;

@Mapper
public interface FriendMapper {
    void sendRequest(@Param("userId") int userId, @Param("friendId") int friendId);
    void acceptRequest(@Param("userId") int userId, @Param("friendId") int friendId);
    void rejectRequest(@Param("userId") int userId, @Param("friendId") int friendId);
    void deleteFriend(@Param("userId") int userId, @Param("friendId") int friendId);

    List<Friend> getFriendList(@Param("userId") int userId);
    List<Friend> getIncomingRequests(@Param("userId") int userId);
    List<Friend> getOutgoingRequests(@Param("userId") int userId);
}
