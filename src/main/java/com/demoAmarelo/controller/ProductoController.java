package com.demoAmarelo.controller;

import com.demoAmarelo.model.Mensaje;
import com.demoAmarelo.model.Producto;
import com.demoAmarelo.repository.ProductoRepository;
import com.demoAmarelo.service.DemoService;
import com.demoAmarelo.service.MensajeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private MensajeService mensajeService;

    @Autowired
    DemoService service ;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProducto(@RequestBody Producto producto) {
    	   	 Map<String, Object> response = new HashMap<>();
    	   	Producto productoDTO;
    	  try {
    		   productoDTO = service.createProducto(producto);

    	    } catch (Exception e) {

    	        response.put("mensaje","Error");
    	        response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
    	        return new ResponseEntity<Map<String, Object>>(response, 
    	 HttpStatus.INTERNAL_SERVER_ERROR);
    	    }
    	    if (productoDTO == null) {
    	        response.put("mensaje", "");
    	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    	    }

	        response.put("Response",producto);
    	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable String id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable String id, @RequestBody Producto productoDetails) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setPrecio(productoDetails.getPrecio());
                    return ResponseEntity.ok(productoRepository.save(producto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProducto(@PathVariable String id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    productoRepository.delete(producto);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
  /*  @PostMapping("/mensajes")
    public void enviarMensaje(@RequestBody Mensaje mensaje) {
        mensajeService.enviarMensaje(mensaje);
    }*/
}
