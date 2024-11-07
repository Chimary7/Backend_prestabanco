package com.example.TingesoProyect_backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") // Permite todas las rutas
                .allowedOrigins("http://localhost:5173", "http://20.189.161.172:8070") // Permite solicitudes desde tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*"); // Permite todos los encabezados
    }
}
