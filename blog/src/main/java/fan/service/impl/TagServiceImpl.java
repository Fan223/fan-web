package fan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fan.core.util.IdUtil;
import fan.dao.TagDAO;
import fan.lang.*;
import fan.pojo.dto.TagDTO;
import fan.pojo.entity.TagDO;
import fan.pojo.query.TagQuery;
import fan.pojo.vo.TagVO;
import fan.service.TagService;
import fan.utils.BlogMapStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 标签管理实现类
 *
 * @author Fan
 * @since 2023/6/21 13:52
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Resource
    private TagDAO tagDAO;

    @Override
    public Response<Page<TagVO>> pageTags(TagQuery tagQuery) {
        try {
            LambdaQueryWrapper<TagDO> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(StringUtil.INSTANCE.isNotBlank(tagQuery.getFlag()), TagDO::getFlag, tagQuery.getFlag())
                    .like(StringUtil.INSTANCE.isNotBlank(tagQuery.getName()), TagDO::getName, tagQuery.getName());

            Page<TagDO> page = tagDAO.selectPage(new Page<>(tagQuery.getCurrentPage(), tagQuery.getPageSize()), queryWrapper);
            return Response.success(BlogMapStruct.INSTANCE.transTag(page));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Response.fail("查询标签出现异常", null);
        }
    }

    @Override
    public Response<Integer> addTag(TagDTO tagDTO) {
        try {
            TagDO tagDO = BlogMapStruct.INSTANCE.transTag(tagDTO);

            tagDO.setId(String.valueOf(IdUtil.getSnowflakeId()));
            LocalDateTime localDateTime = LocalDateTime.now();
            tagDO.setCreateTime(Timestamp.valueOf(localDateTime));
            tagDO.setUpdateTime(Timestamp.valueOf(localDateTime));

            return Response.success(tagDAO.insert(tagDO));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Response.fail("新增标签出现异常", null);
        }
    }

    @Override
    public Response<Integer> updateTag(TagDTO tagDTO) {
        try {
            TagDO tagDO = BlogMapStruct.INSTANCE.transTag(tagDTO);

            tagDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            return Response.success(tagDAO.updateById(tagDO));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Response.fail("更新标签出现异常", null);
        }
    }

    @Override
    public Response<Integer> deleteTag(List<String> ids) {
        try {
            return Response.success(tagDAO.deleteBatchIds(ids));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Response.fail("删除标签出现异常", null);
        }
    }
}
