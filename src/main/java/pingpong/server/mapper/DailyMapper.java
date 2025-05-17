package pingpong.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pingpong.server.domain.Daily;

import java.util.List;

@Mapper
public interface DailyMapper {
    void create(Daily daily);
    List<Daily> getList(@Param("userId") int userId);
    void update(Daily daily);
    void delete(@Param("id") int id);
}
