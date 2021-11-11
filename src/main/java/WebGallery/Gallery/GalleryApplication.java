package WebGallery.Gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GalleryApplication {

	private static final String PROPERTIES =
			"spring.config.location="
			+"classpath:/application.yml"
			+",classpath:/security.yml";

	public static void main(String[] args) {
		SpringApplication.run(GalleryApplication.class, args);

//		new SpringApplicationBuilder(GalleryApplication.class)
//				.properties(PROPERTIES)
//				.run(args);
	}

}
