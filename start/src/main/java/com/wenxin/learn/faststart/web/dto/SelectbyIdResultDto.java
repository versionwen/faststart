package com.wenxin.learn.faststart.web.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * mapper.OrderMapper.selectbyId的查询结果集，该代码由mybatis-plus-generator-ui自动生成
 *
 * @author version
 * @since 2020-08-28
 */
@Data
@NoArgsConstructor
public class SelectbyIdResultDto {

    private Integer id;

    private String name;

    private Integer count;

    private Integer sale;

    private Integer version;

    private Integer stockid;

    private Integer sid;

    private String stockname;

    private String orderId;

    private Date createTime;

}
