package pingpong.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.UserDaily;
import pingpong.server.domain.UserDiaryRole;
import pingpong.server.mapper.UserDailyMapper;

@Service
@RequiredArgsConstructor
public class UserDailyService {

    private final UserDailyMapper userDailyMapper;

    public List<UserDaily> getMembers(int dailyId) {
        return userDailyMapper.getMembers(dailyId);
    }

    public void inviteMember(int requesterId, int dailyId, int userId) {
        UserDaily requester = userDailyMapper.getUserDaily(dailyId, requesterId);
        if (requester == null || requester.getRole() != UserDiaryRole.OWNER) {
            throw new IllegalArgumentException("일정 초대 권한이 없습니다.");
        }

        if (userDailyMapper.isUserInDaily(dailyId, userId)) {
            throw new IllegalArgumentException("이미 초대된 사용자입니다.");
        }

        userDailyMapper.inviteMember(dailyId, userId);
    }

    public void removeMember(int requesterId, int dailyId, int userId) {
        UserDaily requester = userDailyMapper.getUserDaily(dailyId, requesterId);
        if (requester == null || requester.getRole() != UserDiaryRole.OWNER) {
            throw new IllegalArgumentException("일정 삭제 권한이 없습니다.");
        }

        userDailyMapper.removeMember(dailyId, userId);
    }

    public void addOwner(int dailyId, int userId) {
        userDailyMapper.inviteMemberAsOwner(dailyId, userId);
    }
}
