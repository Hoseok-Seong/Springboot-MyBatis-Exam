package shop.mtcoding.exam.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardRepository {
    public List<Board> findById(int userId);
}
