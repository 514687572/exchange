# 2018-07-12 添加广告的语言字段
ALTER TABLE t_advertise ADD COLUMN `locale` varchar(8)  NOT NULL COMMENT '语言版本， 格式: 语言_国家， 比如zh_CN,zh_TW, en_US';
# 2018-07-13 删除操作日志表的last_time字段，把operation_time改成datetime格式
# 2018-07-16
ALTER TABLE t_article ADD COLUMN `display_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '显示时间，如果不设置就是最后更新时间';

INSERT INTO `t_config` (`conf_name`,`conf_value`,`comment`) VALUES ('official_group.wx_image_link','','官方微信名片图片链接');
INSERT INTO `t_config` (`conf_name`,`conf_value`,`comment`) VALUES ('official_group.qq_link','https://jq.qq.com/?_wv=1027&k=5iwc21l','官方qq群链接');


