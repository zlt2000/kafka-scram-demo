package org.example.kafka.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.DescribeAclsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.common.acl.*;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourcePatternFilter;
import org.apache.kafka.common.resource.ResourceType;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
public class KafkaAclService {
    private final KafkaAdminClient kafkaAdminClient;

    public void createAcl(String account, String topicName, String consumerGroup) throws ExecutionException, InterruptedException {
        AclBinding aclBindingTopic = genAclBinding(account, ResourceType.TOPIC, topicName, AclOperation.READ);
        AclBinding aclBindingGroup = genAclBinding(account, ResourceType.GROUP, consumerGroup, AclOperation.READ);
        kafkaAdminClient.createAcls(List.of(aclBindingTopic, aclBindingGroup));
    }

    public void delAcl(String account, String topicName, String consumerGroup) throws ExecutionException, InterruptedException {
        AclBindingFilter aclBindingTopic = genAclBindingFilter(account, ResourceType.TOPIC, topicName, AclOperation.READ);
        AclBindingFilter aclBindingGroup = genAclBindingFilter(account, ResourceType.GROUP, consumerGroup, AclOperation.READ);
        kafkaAdminClient.deleteAcls(List.of(aclBindingTopic, aclBindingGroup));
    }

    public Collection<String> describeAllAcl() throws ExecutionException, InterruptedException {
        return this.describeAcl(AclBindingFilter.ANY);
    }

    public Collection<String> describeTopicAcl(String topicName) throws ExecutionException, InterruptedException {
        AclBindingFilter filter = new AclBindingFilter(new ResourcePatternFilter(ResourceType.TOPIC, topicName, PatternType.ANY)
                , AccessControlEntryFilter.ANY);
        return this.describeAcl(filter);
    }

    private Collection<String> describeAcl(AclBindingFilter filter) throws ExecutionException, InterruptedException {
        DescribeAclsResult result = kafkaAdminClient.describeAcls(filter);
        Collection<AclBinding> gets = result.values().get();
        return gets.stream().map(
                e -> e.entry().principal() + "-" + e.entry().host() + "-" + e.entry().operation() + "-" + e.entry().permissionType()
        ).toList();
    }

    private AclBinding genAclBinding(String account, ResourceType resourceType, String resourceName, AclOperation aclOperation) {
        //绑定资源
        ResourcePattern resourcePatternGroup = new ResourcePattern(resourceType, resourceName, PatternType.LITERAL);
        //绑定用户、权限
        AccessControlEntry accessControlEntryRead = new AccessControlEntry("User:" + account, "*", aclOperation, AclPermissionType.ALLOW);
        return new AclBinding(resourcePatternGroup, accessControlEntryRead);
    }

    private AclBindingFilter genAclBindingFilter(String account, ResourceType resourceType, String resourceName, AclOperation aclOperation) {
        //绑定资源
        ResourcePatternFilter resourcePatternFilterGroup = new ResourcePatternFilter(resourceType, resourceName, PatternType.LITERAL);
        //绑定用户、权限
        AccessControlEntryFilter accessControlEntryFilter = new AccessControlEntryFilter("User:" + account, "*", aclOperation, AclPermissionType.ALLOW);
        return new AclBindingFilter(resourcePatternFilterGroup, accessControlEntryFilter);
    }
}
