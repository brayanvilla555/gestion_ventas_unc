package org.msvc_cobro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcCobrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCobrosApplication.class, args);
	}

}
