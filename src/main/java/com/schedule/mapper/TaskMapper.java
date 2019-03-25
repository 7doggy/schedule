package com.schedule.mapper;

import com.schedule.model.TaskDO;
import com.schedule.model.vo.TaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    TaskDO get(Long id);

    List<TaskDO> list();

    List<TaskVO> listTaskVoByDesc(@Param("desc") String desc);

    int save(TaskDO task);

    int update(TaskDO task);

    int remove(Long id);

    int removeBatch(Long[] ids);
}
