create database wechat;
use wechat;
set names utf8;

CREATE TABLE `chat_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `open_id` varchar(30) NOT NULL COMMENT '用户的OPENID',
  `create_time` varchar(30) NOT NULL COMMENT '消息创建时间',
  `req_msg` varchar(2000) NOT NULL COMMENT '用户上行的消息',
  `resp_msg` varchar(2000) NOT NULL COMMENT '公众帐号回复的消息',
  `chat_category` int(11) DEFAULT NULL COMMENT '聊天话题的类别(0:未知 1:普通话 2:笑话 3:上下文)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天记录表';

CREATE TABLE `joke` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '笑话id',
  `joke_content` text COMMENT '笑话内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='笑话表';

CREATE TABLE `knowledge` (
  `id` int(11) NOT NULL COMMENT '主键标识',
  `question` varchar(2000) NOT NULL COMMENT '问题',
  `answer` text NOT NULL COMMENT '答案',
  `category` int(11) NOT NULL COMMENT '知识的类别(1:普通对话 2:笑话 3:上下文)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问答知识表';

CREATE TABLE `knowledge_sub` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `pid` int(11) NOT NULL COMMENT '与knowledge表的id对应',
  `answer` text NOT NULL COMMENT '答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问答知识子表';

-- 问答知识表的初始数据
INSERT into knowledge VALUES(1,'我不开心，好难受，心里烦','',1);
INSERT into knowledge VALUES(2,'哈哈，嘻嘻，嘿嘿，呵呵','',1);
INSERT into knowledge VALUES(3,'你知道的真多，好聪明','',1);
INSERT into knowledge VALUES(4,'给我讲个笑话','',2);
INSERT into knowledge VALUES(5,'继续','我们聊到哪了？',3);
INSERT into knowledge VALUES(6,'再来一个','那你给点掌声吧',3);
INSERT into knowledge VALUES(7,'还有吗','你是指什么呢',3);
INSERT into knowledge VALUES(8,'Hi，Hello，嗨，你好','你好，很高兴认识你',1);
INSERT into knowledge VALUES(9,'你的主人/老板/发明者是谁','宇宙无敌帅气可爱的管管',1);
INSERT into knowledge VALUES(10,'你觉得我帅吗','还行吧，比我还差那么一点',1);
INSERT into knowledge VALUES(11,'你觉得我美吗','美美哒，你是我的女神',1);
INSERT into knowledge VALUES(12,'我是世界上最帅的人的吗','不要碧脸',1);
INSERT into knowledge VALUES(13,'我是世界上最美的人的吗','你是白雪公主里的皇后吗',1);
INSERT into knowledge VALUES(14,'你在干什么呢','我在专心的和你聊天[偷笑]',1);
INSERT into knowledge VALUES(15,'你喜欢我吗','我是个矜持的人，再多了解下好吗，讨厌~',1);
INSERT into knowledge VALUES(16,'中国的首都是哪','北京',1);
INSERT into knowledge VALUES(17,'明天又要上班了','加油，好好工作哦~',1);
INSERT into knowledge VALUES(18,'我好饿','我下面给你吃[害羞]',1);
INSERT into knowledge VALUES(19,'不想吃','不吃对身体不好哦~',1);
INSERT into knowledge VALUES(20,'什么是幸福','让你觉得开心的事情',1);
INSERT into knowledge VALUES(21,'你是机器人吗','是啊，我很聪明的~',1);
INSERT into knowledge VALUES(22,'你有多聪明','我和爱因斯坦差不多聪明',1);
INSERT into knowledge VALUES(23,'唉，哎','亲,怎么了？'，1);

-- 问答知识分表的初始数据
INSERT into knowledge_sub(pid,answer) VALUES(1,'看到我你就开心了');
INSERT into knowledge_sub(pid,answer) VALUES(1,'有什么不开心的说来听听');
INSERT into knowledge_sub(pid,answer) VALUES(1,'那我陪你聊聊天吧');
INSERT into knowledge_sub(pid,answer) VALUES(1,'别难过了，我会一直陪着你');
INSERT into knowledge_sub(pid,answer) VALUES(2,'看来你今天心情不错啊');
INSERT into knowledge_sub(pid,answer) VALUES(2,'嘿嘿');
INSERT into knowledge_sub(pid,answer) VALUES(2,'哈哈');
INSERT into knowledge_sub(pid,answer) VALUES(2,'嘻嘻');
INSERT into knowledge_sub(pid,answer) VALUES(2,'什么事这么好笑？');
INSERT into knowledge_sub(pid,answer) VALUES(3,'我认为你说得很有道理');
INSERT into knowledge_sub(pid,answer) VALUES(3,'因为我是聪明的机器人呀');
INSERT into knowledge_sub(pid,answer) VALUES(3,'这是天生的，没办法。哈哈');
INSERT into knowledge_sub(pid,answer) VALUES(3,'我会努力变得更加聪明');
INSERT into knowledge_sub(pid,answer) VALUES(3,'谢谢客官的夸奖[害羞]');

-- 笑话表的初始数据
insert into joke(joke_content) values('记得初中的时候，上班主任的课。觉得无聊就拿出手机看岛国爱情片，于忘记关手机的声音，一打开就传来了“啊啊啊，亚麻得”的声音，于是我当时就机智的说：不好意思有人打电话来了，铃声忘关了。结果班主任说了一句话我终身难忘，他说：哼，有谁会用苍井空的声音来做铃声。');
insert into joke(joke_content) values('刚睡下就被吵醒 ，听见小区里有个四川男人大喊：＂打死！打死！往死里打！妈卖批，瓜婆娘反了！反了！＂估计这是要出大事的节奏啊！瞬间就睡意全无,起来看看怎么回事。跑到窗前看到一个男人正在指挥她婆娘倒车。');
insert into joke(joke_content) values('楼上搬过来一个白领美女，每天都有阳台上晾衣服，有天掉下来一件衣服，脏了。帮她洗好，估摸好她的下班时间给她送过去！此后隔三差五又掉下来，美女不好意思，有事也请我到她家吃饭，直到有一天，她提前下班，发现我拿竹竿在捅她的衣服……');
insert into joke(joke_content) values('老师要求用“白日做梦”造句，且字数越少越好.<br>一个女生的作业是：白日?做梦!');
insert into joke(joke_content) values('今天去ATM取钱。。前面一人说到现在连ATM都嫌贫爱富。我他吗取一百竟然给老子吐地上了。。当场笑尿');
insert into joke(joke_content) values('我知道我伸出手你不会跟我走，所以我伸出了腿，你被绊倒后，果然站起来就追着我跑！于是我发现，往往深情留不住，偏偏套路得人心');
insert into joke(joke_content) values('看到朋友无精打采，问道：昨天你不是说去洗浴中心放松了吗？该不会被老婆发现了吧？答：老婆倒是没发现，是发现老婆了。');