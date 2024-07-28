package org.example.kafka.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author: zlt
 * @date: 2024/7/5
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@Service
@AllArgsConstructor
public class KafkaAccountService {
    private final KafkaAdminClient kafkaAdminClient;

    public void createUser(String userName, String password) throws ExecutionException, InterruptedException {
        // 构造Scram认证机制信息
        ScramCredentialInfo info = new ScramCredentialInfo(ScramMechanism.SCRAM_SHA_256, 8192);
        //用户信息
        UserScramCredentialAlteration userScramCredentialAdd = new UserScramCredentialUpsertion(userName, info, password);
        AlterUserScramCredentialsResult result = kafkaAdminClient.alterUserScramCredentials(List.of(userScramCredentialAdd));
        result.all().get();
    }

    public void deleteUser(String userName) throws ExecutionException, InterruptedException {
        //用户信息
        UserScramCredentialDeletion userScramCredentialDel = new UserScramCredentialDeletion(userName, ScramMechanism.SCRAM_SHA_256);
        AlterUserScramCredentialsResult result = kafkaAdminClient.alterUserScramCredentials(List.of(userScramCredentialDel));
        result.all().get();
    }

    public Set<String> describeAccount() throws ExecutionException, InterruptedException {
        //查询所有的账户，这也是默认方法
        DescribeUserScramCredentialsResult result = kafkaAdminClient.describeUserScramCredentials();
        Map<String, UserScramCredentialsDescription> future = result.all().get();
        future.forEach((name, info) -> System.out.println("[ScramUserName:" + name + "]:[ScramUserInfo:" + info.toString() + "]"));
        return future.keySet();
    }
}
