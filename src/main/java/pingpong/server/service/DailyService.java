package pingpong.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.Daily;
import pingpong.server.dto.request.DailyRequestDto;
import pingpong.server.mapper.DailyMapper;
import pingpong.server.mapper.UserDailyMapper;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyMapper dailyMapper;
    private final UserDailyMapper userDailyMapper;

    public void create(int userId, DailyRequestDto request) {
        Daily daily = new Daily();
        daily.setOwnerId(userId);
        daily.setTitle(request.getTitle());
        daily.setContent(request.getContent());

        dailyMapper.create(daily);
        userDailyMapper.inviteMemberAsOwner(daily.getId(), userId);
    }

    public List<Daily> getList(int userId) {
        return dailyMapper.getList(userId);
    }

    public void update(int id, DailyRequestDto request) {
        Daily daily = new Daily();
        daily.setId(id);
        daily.setTitle(request.getTitle());
        daily.setContent(request.getContent());

        dailyMapper.update(daily);
    }

    public void delete(int id) {
        dailyMapper.delete(id);
    }
}
