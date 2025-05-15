package pingpong.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.UserDiary;
import pingpong.server.domain.UserDiaryRole;
import pingpong.server.mapper.UserDiaryMapper;

@Service
@RequiredArgsConstructor
public class UserDiaryService {

    private final UserDiaryMapper userDiaryMapper;

    public List<UserDiary> getMembers(int diaryId) {
        return userDiaryMapper.getMembers(diaryId);
    }

    public void inviteMember(int requesterId, int diaryId, int userId) {
        UserDiary requester = userDiaryMapper.getUserDiary(diaryId, requesterId);
        if (requester == null || requester.getRole() != UserDiaryRole.OWNER) {
            throw new IllegalArgumentException("다이어리 초대 권한이 없습니다.");
        }

        if (userDiaryMapper.isUserInDiary(diaryId, userId)) {
            throw new IllegalArgumentException("이미 초대된 사용자입니다.");
        }

        userDiaryMapper.inviteMember(diaryId, userId);
    }


    public void removeMember(int requesterId, int diaryId, int userId) {
        UserDiary requester = userDiaryMapper.getUserDiary(diaryId, requesterId);
        if (requester == null || requester.getRole() != UserDiaryRole.OWNER) {
            throw new IllegalArgumentException("다이어리 삭제 권한이 없습니다.");
        }

        userDiaryMapper.removeMember(diaryId, userId);
    }


}
