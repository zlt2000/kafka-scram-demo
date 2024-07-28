package org.example.kafka;

import lombok.AllArgsConstructor;
import org.example.kafka.service.KafkaAccountService;
import org.example.kafka.service.KafkaAclService;
import org.example.kafka.service.KafkaTopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author: zlt
 * @date: 2024/7/5
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@RequestMapping("/kafka")
@RestController
@AllArgsConstructor
public class KafkaController {
    private final KafkaProducer kafkaProducer;
    private final KafkaTopicService topicService;
    private final KafkaAccountService accountService;
    private final KafkaAclService aclService;

    @GetMapping("/send")
    public String sendKafkaMessage() {
        kafkaProducer.sendMessage("Hello, Kafka with Spring Boot!");
        return "Message sent to Kafka";
    }

    @GetMapping("/addTopic")
    public String addTopic(String topicName) throws ExecutionException, InterruptedException {
        topicService.createTopic(topicName, 1, (short) 1);
        return "createTopic " + topicName + " success!";
    }
    @GetMapping("/delTopic")
    public String delTopic(String topicName) throws ExecutionException, InterruptedException {
        topicService.delTopic(topicName);
        return "delTopic " + topicName + " success!";
    }
    @GetMapping("/listTopic")
    public Collection<String> listTopic() throws ExecutionException, InterruptedException {
        return topicService.listTopic();
    }

    @GetMapping("/addUser")
    public String addUser(String username, String password) throws ExecutionException, InterruptedException {
        accountService.createUser(username, password);
        return "addUser " + username + " success!";
    }
    @GetMapping("/delUser")
    public String delUser(String username) throws ExecutionException, InterruptedException {
        accountService.deleteUser(username);
        return "delUser " + username + " success!";
    }
    @GetMapping("/listUser")
    public Set<String> listUser() throws ExecutionException, InterruptedException {
        return accountService.describeAccount();
    }

    @GetMapping("/addAcl")
    public String addAcl(String account, String topicName, String group) throws ExecutionException, InterruptedException {
        aclService.createAcl(account, topicName, group);
        return "addAcl " + account + " success!";
    }

    @GetMapping("/delAcl")
    public String delAcl(String account, String topicName, String group) throws ExecutionException, InterruptedException {
        aclService.delAcl(account, topicName, group);
        return "delAcl " + account + " success!";
    }

    @GetMapping("/lsitAcl")
    public Collection<String> lsitAcl() throws ExecutionException, InterruptedException {
        return aclService.describeAllAcl();
    }

    @GetMapping("/lsitTopicAcl")
    public Collection<String> lsitTopicAcl(String topicName) throws ExecutionException, InterruptedException {
        return aclService.describeTopicAcl(topicName);
    }
}