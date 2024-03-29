package fan.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 菜单实体类
 *
 * @author Fan
 * @since 2023/6/9 11:16
 */
@Data
@TableName("menu")
public class MenuDO {

    @TableId
    private String id;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String parentId;

    private String position;

    private String name;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String path;

    private String permission;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String component;

    private Integer type;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String icon;

    private Integer orderNum;

    private String flag;

    private Timestamp createTime;

    private Timestamp updateTime;
}
