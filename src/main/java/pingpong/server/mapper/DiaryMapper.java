package pingpong.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pingpong.server.domain.Diary;
import pingpong.server.dto.CalendarDiaryDto;
import pingpong.server.dto.response.DiaryResponseDto;

@Mapper
public interface DiaryMapper {
    void createDiary(Diary diary);
    List<Diary> getDiaryList(int userId);
    Diary getDiaryById(int id);
    void updateDiary(Diary diary);
    void deleteDiary(@Param("id") int id);
    List<DiaryResponseDto> getDiarySharedList(@Param("userId") int userId);
    List<CalendarDiaryDto> getCalendarEmotionList(@Param("userId") int userId);

}
