package com.demoAmarelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.demoAmarelo.model.Producto;
import com.demoAmarelo.repository.ProductoRepository;

@Service
public class DemoService {

	 @Autowired
	    private ProductoRepository productoRepository;
	 @Autowired
	 private MensajeService mensaje;
	 
	 public Producto createProducto(Producto producto) {
		 try {
		//	 mensaje.sendQueve(producto);
			 
			 return productoRepository.save(producto);
		 }catch (Exception e) {
			 mensaje.sendQueve(producto);
			 return null;
		}
	       
	    }
	    
	 
}
