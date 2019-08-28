SET FOREIGN_KEY_CHECKS=0;



-- Table structure for leave_message_likes_record

-- ----------------------------
DROP TABLE IF EXISTS `leave_message_likes_record`;
CREATE TABLE `leave_message_likes_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pageName` varchar(255) NOT NULL,
  `pId` int(11) NOT NULL,
  `likerId` int(11) NOT NULL,
  `likeDate` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leave_message_likes_record
-- ----------------------------

-- ----------------------------
-- Table structure for leave_message_record
-- ----------------------------
DROP TABLE IF EXISTS `leave_message_record`;
CREATE TABLE `leave_message_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pageName` varchar(255) NOT NULL,
  `PId` int(255) NOT NULL,
  `answererId` int(11) NOT NULL,
  `RespondentId` int(11) NOT NULL,
  `MoodMessageDate` varchar(255) NOT NULL,
  `likes` int(11) NOT NULL,
  `MoodMessageContent` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leave_message_record
-- ----------------------------
INSERT INTO `leave_message_record` VALUES ('14', 'categories', '0', '1', '1', '2018-09-19 13:53', '0', '分类留言测试');
INSERT INTO `leave_message_record` VALUES ('15', 'archives', '0', '1', '1', '2018-09-19 13:53', '0', '归档留言');
INSERT INTO `leave_message_record` VALUES ('16', 'tags', '0', '1', '1', '2018-09-19 13:53', '0', '标签留言');
INSERT INTO `leave_message_record` VALUES ('17', 'update', '0', '1', '1', '2018-09-19 13:53', '0', '更新留言');
INSERT INTO `leave_message_record` VALUES ('18', 'friendlylink', '0', '1', '1', '2018-09-19 13:54', '0', '需要添加友链的朋友可在www.zhyocean.cn/friendlylink下方留言（网站名称+网址），随后验证后会在本人博客中添加友链链接');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
#                       `phone` varchar(255) NOT NULL,
                      `username` varchar(255) NOT NULL,
                      `password` varchar(255) NOT NULL,
                      `gender` char(255) NOT NULL,
#                       `trueName` varchar(255) DEFAULT NULL,
#                       `birthday` varchar(255) DEFAULT NULL,
#                       `email` varchar(255) DEFAULT NULL,
                      `personalBrief` varchar(255) DEFAULT NULL,
                      `avatarImgUrl` text NOT NULL,
#                       `recentlyLanded` varchar(255) DEFAULT NULL,
                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
# INSERT INTO `user` VALUES ('1', '19940790216', '周轲', 'a3caed36f0fe5a01e5f144db8927235e', 'male', '周轲', '1997-07-05', '229400458@qq.com', '', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/周轲/1536759681.jpeg', '2018-09-19 13:52:50');
INSERT INTO `user` VALUES ('1', '周轲', 'a3caed36f0fe5a01e5f144db8927235e', 'male', 'happy boy','https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/周轲/1536759681.jpeg');
