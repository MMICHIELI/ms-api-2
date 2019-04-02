package com.msapi2.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * API-Gateway for MS-API-2
 * @link @EnableZuulProxy turns this application as Netflix Zuul Gateway
 * @link @EnableDiscoveryClient allows this app to register with Discovery
 * @link @EnableHystrix allows this app to be managed by Hystrix
 */
@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@EnableDiscoveryClient
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

}
