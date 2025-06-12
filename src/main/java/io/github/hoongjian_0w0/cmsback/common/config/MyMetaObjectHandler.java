package io.github.hoongjian_0w0.cmsback.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.hoongjian_0w0.cmsback.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getCurrentUserIdSafe();

        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getCurrentUserIdSafe();

        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    private Long getCurrentUserIdSafe() {
        try {
            return SecurityUtils.getCurrentUserId();
        } catch (Exception e) {
            return null; // If not logged in (e.g., background task)
        }
    }
}