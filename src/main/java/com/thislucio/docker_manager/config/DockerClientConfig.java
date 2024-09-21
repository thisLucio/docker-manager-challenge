package com.thislucio.docker_manager.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {

    @Value("${docker.socket.path}")
    private String dockerSocketPath;

    @Bean
    public DockerClient buildDockerClient(){
        DefaultDockerClientConfig.Builder defaultDockerClientConfigBuilder = DefaultDockerClientConfig
                .createDefaultConfigBuilder();

        if (this.dockerSocketPath != null && this.dockerSocketPath.startsWith("unix://")) {
            defaultDockerClientConfigBuilder.withDockerHost(dockerSocketPath)
                    .withDockerTlsVerify(false);
        }

        DefaultDockerClientConfig dockerClientConfig = defaultDockerClientConfigBuilder
                .build();

        ApacheDockerHttpClient dpckerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost()).build();


        return DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerHttpClient(dpckerHttpClient)
                .build();
    }

}
