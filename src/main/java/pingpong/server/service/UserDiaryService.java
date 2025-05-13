package pingpong.server.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pingpong.server.domain.UserDiary;
import pingpong.server.mapper.UserDiaryMapper;

@Service
@RequiredArgsConstructor
public class UserDiaryService {

    private final UserDiaryMapper userDiaryMapper;

    public List<UserDiary> getMembers(int diaryId) {
        return userDiaryMapper.getMembers(diaryId);
    }

    public void inviteMember(int diaryId, int userId) {
        userDiaryMapper.inviteMember(diaryId, userId);
    }

    public void removeMember(int diaryId, int userId) {
        userDiaryMapper.removeMember(diaryId, userId);
    }
}
