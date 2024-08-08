## 文章地址

[Spring Boot 基于 SCRAM 认证集成 Kafka 的详解](https://mp.weixin.qq.com/s/fHE3RqKih-IseqtojRfsIw?token=567067404&lang=zh_CN)




## 发送消息

http://localhost:7000/kafka/send



## topic 管理
http://localhost:7000/kafka/addTopic?topicName=test3
http://localhost:7000/kafka/delTopic?topicName=test789
http://localhost:7000/kafka/listTopic



## 用户管理
http://localhost:7000/kafka/addUser?username=test2&password=test2
http://localhost:7000/kafka/delUser?username=test2
http://localhost:7000/kafka/listUser



## 权限管理
http://localhost:7000/kafka/addAcl?account=test2&topicName=test&group=test-consumer-group
http://localhost:7000/kafka/delAcl?account=test2&topicName=test&group=test-consumer-group
http://localhost:7000/kafka/lsitTopicAcl?topicName=test
http://localhost:7000/kafka/lsitAcl
