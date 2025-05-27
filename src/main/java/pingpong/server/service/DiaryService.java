package pingpong.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.Diary;
import pingpong.server.dto.request.DiaryRequestDto;
import pingpong.server.dto.response.CalendarDiaryDto;
import pingpong.server.dto.response.DiaryResponseDto;
import pingpong.server.mapper.DiaryMapper;
import pingpong.server.mapper.UserDiaryMapper;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;
    private final UserDiaryMapper userDiaryMapper;

    public void createDiary(int userId, DiaryRequestDto request) {
        Diary diary = new Diary();
        diary.setOwnerId(userId);
        diary.setEmotionColor(request.getEmotionColor());
        diary.setWeather(request.getWeather());
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setShared(request.isShared());

        diaryMapper.createDiary(diary);
        
        userDiaryMapper.inviteMemberAsOwner(diary.getId(), userId);
        
        if (request.isShared() && request.getMembers() != null) {
            for (Integer receiverId : request.getMembers()) {
                userDiaryMapper.inviteMemberAsReceiver(diary.getId(), receiverId);
            }
        }
    }
    
    public List<DiaryResponseDto> getDiarySharedList(int userId) {
        return diaryMapper.getDiarySharedList(userId); 
    }


    public List<Diary> getDiaryList(int userId) {
        return diaryMapper.getDiaryList(userId);
    }

    public Diary getDiary(int id) {
        return diaryMapper.getDiaryById(id);
    }

    public void updateDiary(int id, DiaryRequestDto request) {
        Diary diary = new Diary();
        diary.setId(id);
        diary.setEmotionColor(request.getEmotionColor());
        diary.setWeather(request.getWeather());
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());

        diaryMapper.updateDiary(diary);
    }

    public void deleteDiary(int id) {
        diaryMapper.deleteDiary(id);
    }
    
    public List<CalendarDiaryDto> getCalendarEmotionList(int userId) {
        return diaryMapper.getCalendarEmotionList(userId);
    }

}
