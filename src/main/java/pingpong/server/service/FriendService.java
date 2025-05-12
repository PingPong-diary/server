package pingpong.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pingpong.server.domain.Friend;
import pingpong.server.mapper.FriendMapper;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendMapper friendMapper;

    public void sendRequest(int userId, int friendId) {
        friendMapper.sendRequest(userId, friendId);
    }

    public void acceptRequest(int userId, int friendId) {
        friendMapper.acceptRequest(userId, friendId);
    }

    public void rejectRequest(int userId, int friendId) {
        friendMapper.rejectRequest(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        friendMapper.deleteFriend(userId, friendId);
    }

    public List<Friend> getFriendList(int userId) {
        return friendMapper.getFriendList(userId);
    }

    public List<Friend> getIncomingRequests(int userId) {
        return friendMapper.getIncomingRequests(userId);
    }

    public List<Friend> getOutgoingRequests(int userId) {
        return friendMapper.getOutgoingRequests(userId);
    }
}
