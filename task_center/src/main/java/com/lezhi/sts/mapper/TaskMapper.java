package com.lezhi.sts.mapper;

import com.lezhi.sts.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface TaskMapper {

    int insert(Task record);

    Task fetchOneTask(@Param("group") String group, @Param("performer") String performer);

    int startTask(@Param("id") Integer id, @Param("performer") String performer, @Param("startTime") Date startTime);

    int finishTask(@Param("id") Integer id, @Param("performer") String performer, @Param("status") int status);

    int autoTimeout();

    Integer maxArchiveId();

    int migrateToHistory(@Param("maxId") Integer maxId);

    int removeArchived(@Param("maxId") Integer maxId);

    void invalidRetryManyTimes();
}