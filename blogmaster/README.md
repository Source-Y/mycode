1. 配置自己的redis地址
2. 创建以下表
3. 运行项目后， 打开http://localhost:8888/doc.html进行测试

Create Table:
CREATE TABLE `t_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否被封禁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3

CREATE TABLE `t_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `summary` varchar(255) DEFAULT NULL COMMENT '简介',
  `comment_count` int DEFAULT NULL COMMENT '评论数',
  `view_count` int DEFAULT NULL COMMENT '浏览量',
  `author_id` bigint DEFAULT NULL COMMENT '作者id',
  `category_id` bigint DEFAULT NULL COMMENT '类目id',
  `create_date` bigint DEFAULT NULL COMMENT '创建时间',
  `favor` bigint DEFAULT NULL COMMENT '点赞数',
  `body_id` bigint DEFAULT NULL COMMENT '文章主体id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3

CREATE TABLE `t_body` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  `content` longtext COMMENT '文章内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3

CREATE TABLE `t_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(255) DEFAULT NULL COMMENT '类别名称',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3

CREATE TABLE `t_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  `username` varchar(255) DEFAULT NULL COMMENT '评论人',
  `comment_to` bigint DEFAULT NULL COMMENT '给谁评论',
  `create_date` bigint DEFAULT NULL COMMENT '评论时间',
  `is_father` tinyint(1) DEFAULT NULL COMMENT '是否是父评论',
  `favor` bigint DEFAULT NULL COMMENT '评论点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3



